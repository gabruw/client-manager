package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.uol.gabriel.dto.CreateTokenDTO;
import com.compasso.uol.gabriel.dto.LoginDTO;
import com.compasso.uol.gabriel.dto.RefreshTokenDTO;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.security.config.JwtTokenUtil;
import com.compasso.uol.gabriel.service.AuthenticationService;
import com.compasso.uol.gabriel.service.ClientService;
import com.compasso.uol.gabriel.utils.Messages;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	private static final String BEARER_PREFIX = "Bearer ";
	private static final String TOKEN_HEADER = "Authorization";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ClientService clientService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<Response<CreateTokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Iniciando busca da autenticação: {}", loginDTO.toString());
		Response<CreateTokenDTO> response = new Response<CreateTokenDTO>();

		if (result.hasErrors()) {
			log.error("Erro validando dados de login da autenticação: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		Optional<Authentication> authOpt = this.authenticationService.findByEmail(loginDTO.getEmail());
		if (!authOpt.isPresent()) {
			log.info("Autenticação não encontrada para o e-mail: {}", loginDTO.getEmail());
			response.addError(
					Messages.getAuthentication(AuthenticationMessage.INVALIDEMAIL.toString(), loginDTO.getEmail()));

			return ResponseEntity.status(403).body(response);
		}

		boolean isEqual = new BCryptPasswordEncoder().matches(loginDTO.getPassword(), authOpt.get().getPassword());
		if (!isEqual) {
			log.info("Autenticação com a senha incorreta: {}", loginDTO.getPassword());
			response.addError(Messages.getAuthentication(AuthenticationMessage.INVALIDPASSWORD.toString(),
					loginDTO.getPassword()));

			return ResponseEntity.status(403).body(response);
		}

		Optional<Client> clientOpt = this.clientService.findById(authOpt.get().getClient().getId());
		UsernamePasswordAuthenticationToken securityAuthentication = new UsernamePasswordAuthenticationToken(
				loginDTO.getEmail(), loginDTO.getPassword());

		org.springframework.security.core.Authentication authentication = authenticationManager
				.authenticate(securityAuthentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CreateTokenDTO token = new CreateTokenDTO();
		token.setRole(authOpt.get().getRole());
		token.setEmail(authOpt.get().getEmail());
		token.setName(clientOpt.get().getName());

		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
		token.setToken(jwtTokenUtil.create(userDetails));

		response.setData(token);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response<RefreshTokenDTO>> refresh(HttpServletRequest request) {
		log.info("Regerando token.");
		Response<RefreshTokenDTO> response = new Response<RefreshTokenDTO>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.addError(Messages.getAuthentication(AuthenticationMessage.WITHOUTTOKEN.toString()));
		} else if (!jwtTokenUtil.isValid(token.get())) {
			response.addError(Messages.getAuthentication(AuthenticationMessage.INVALIDTOKEN.toString()));
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}

		String refreshedToken = jwtTokenUtil.refresh(token.get());

		RefreshTokenDTO refreshed = new RefreshTokenDTO();
		refreshed.setToken(refreshedToken);

		response.setData(refreshed);
		return ResponseEntity.ok(response);
	}
}
