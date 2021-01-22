package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.entity.City;

public interface CityService {
	void deleteById(Long id);

	Optional<City> findById(Long id);

	Optional<City> findByName(String name);

	Optional<City> findByState(String state);

	List<OptionDTO<Long>> findOptions();

	City persistir(City city);
}
