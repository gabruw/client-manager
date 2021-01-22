package com.compasso.uol.gabriel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
	
	@Transactional(readOnly = true)
	Optional<City> findByName(String name);
	
	@Transactional(readOnly = true)
	Optional<City> findByState(String state);
}
