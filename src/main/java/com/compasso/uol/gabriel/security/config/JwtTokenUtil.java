package com.compasso.uol.gabriel.security.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_AUDIENCE = "audience";

	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.secret}")
	private String secret;

	private Date getExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;

		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}

		return expiration;
	}

	/**
	 * Retorna o email encriptado no token.
	 * 
	 * @param token
	 * @return String
	 */
	public String getUsername(String token) {
		String username;

		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}

		return username;
	}

	/**
	 * Retorna as roles encriptadas no token.
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;

		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}

		return claims;
	}

	public boolean isValid(String token) {
		return !isExpired(token);
	}

	private boolean isExpired(String token) {
		Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null) {
			return false;
		}

		return expirationDate.before(new Date());
	}

	private String createWithRoles(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(getExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String create(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());

		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());

		return createWithRoles(claims);
	}

	public String refresh(String token) {
		String refreshedToken;

		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());

			refreshedToken = createWithRoles(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}

		return refreshedToken;
	}
}