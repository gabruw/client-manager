package com.compasso.uol.gabriel.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.repository.CityRepository;

@SpringBootTest
public class CityServiceTest {

	@MockBean
	private CityRepository cityRepository;

	@Autowired
	private CityService cityService;

	private static final Long ID = 1L;
	private static final String NAME = "Juiz de Fora";
	private static final String STATE = "Minas Gerais";

	@BeforeEach
	public void init() throws Exception {
		when(this.cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new City()));
		when(this.cityRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(new City()));
		when(this.cityRepository.findByState(Mockito.anyString())).thenReturn(Optional.of(new City()));

		when(this.cityRepository.save(Mockito.any(City.class))).thenReturn(new City());
	}

	@Test
	public void findCityById() {
		Optional<City> city = this.cityService.findById(ID);
		assertTrue(city.isPresent());
	}

	@Test
	public void findCityByName() {
		Optional<City> city = this.cityService.findByName(NAME);
		assertTrue(city.isPresent());
	}

	@Test
	public void findCityByState() {
		Optional<City> city = this.cityService.findByState(STATE);
		assertTrue(city.isPresent());
	}

	@Test
	public void presistCity() {
		City city = this.cityService.persistir(new City());
		assertNotNull(city);
	}
}
