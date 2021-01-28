package com.compasso.uol.gabriel.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.repository.CityRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CityServiceTest {

	@MockBean
	private CityRepository cityRepository;

	@Autowired
	private CityService cityService;

	@Mock
	private City city;

	private static final Long ID = 1L;
	private static final String NAME = "Juiz de Fora";
	private static final String STATE = "Minas Gerais";

	@BeforeEach
	public void setup() {
		city = new City();
		city.setName(NAME);
		city.setState(STATE);
	}

	@Test
	public void find_all_cities() {
		List<City> cities = new ArrayList<City>();
		cities.add(city);

		when(this.cityRepository.findAll()).thenReturn(cities);

		List<ReturnCityDTO> options = this.cityService.findAll();
		Assertions.assertTrue(options.size() == 1);
	}

	@Test
	public void find_city_by_id() {
		when(this.cityRepository.findById(ID)).thenReturn(Optional.of(city));

		Optional<City> tCity = this.cityService.findById(ID);
		Assertions.assertTrue(tCity.isPresent());
	}

	@Test
	public void find_city_by_name() {
		when(this.cityRepository.findByName(NAME)).thenReturn(Optional.of(city));

		Optional<City> tCity = this.cityService.findByName(NAME);
		Assertions.assertTrue(tCity.isPresent());
	}

	@Test
	public void find_city_by_state() {
		when(this.cityRepository.findByState(STATE)).thenReturn(Optional.of(city));

		Optional<City> tCity = this.cityService.findByState(STATE);
		Assertions.assertTrue(tCity.isPresent());
	}

	@Test
	public void find_cities_options() {
		List<City> cities = new ArrayList<City>();
		cities.add(city);

		when(this.cityRepository.findAll()).thenReturn(cities);

		List<OptionDTO<Long>> options = this.cityService.findOptions();
		Assertions.assertTrue(options.size() == 1);
	}

	@Test
	public void persist_city() {
		when(this.cityRepository.save(city)).thenReturn(city);

		City tCity = this.cityService.persist(city);
		Assertions.assertEquals(tCity, city);
	}

	@Test
	public void delete_city() {
		when(this.cityRepository.findById(ID)).thenReturn(Optional.of(city));

		this.cityService.deleteById(ID);
		verify(this.cityRepository, times(1)).deleteById(ID);
	}
}
