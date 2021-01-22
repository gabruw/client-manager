package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.uol.gabriel.dto.EditCityDTO;
import com.compasso.uol.gabriel.dto.IncludeCityDTO;
import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.enumerator.message.GenericMessage;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.utils.Messages;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/city")
public class CityController {
	private static final Logger log = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CityService cityService;

	@GetMapping("/find-options")
	public ResponseEntity<Response<List<OptionDTO<Long>>>> findName() throws NoSuchAlgorithmException {
		log.info("Buscando as opções de cidades.");
		Response<List<OptionDTO<Long>>> response = new Response<List<OptionDTO<Long>>>();

		List<OptionDTO<Long>> cities = cityService.findOptions();
		response.setData(cities);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/find-name")
	public ResponseEntity<Response<City>> findName(@RequestParam("name") String name) throws NoSuchAlgorithmException {
		log.info("Buscando a cidade com o nome: {}", name);
		Response<City> response = new Response<City>();

		Optional<City> cityOpt = cityService.findByName(name);
		if (!cityOpt.isPresent()) {
			log.error("Erro ao validar o nome: {}", name);
			response.addError(Messages.getCity(CityMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		response.setData(cityOpt.get());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/find-state")
	public ResponseEntity<Response<City>> findState(@RequestParam("state") String state)
			throws NoSuchAlgorithmException {
		log.info("Buscando o estado com o nome: {}", state);
		Response<City> response = new Response<City>();

		Optional<City> cityOpt = cityService.findByState(state);
		if (!cityOpt.isPresent()) {
			log.error("Erro ao validar o estado: {}", state);
			response.addError(Messages.getCity(CityMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		response.setData(cityOpt.get());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/include")
	public ResponseEntity<Response<City>> include(@Valid @RequestBody IncludeCityDTO cityDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Incluindo o cidade: {}", cityDTO.toString());
		Response<City> response = new Response<City>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para cadastro da cidade: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		Optional<City> cityOpt = this.cityService.findByName(cityDTO.getName());
		if (cityOpt.isPresent()) {
			log.error("Erro ao validar o nome: {}", cityDTO.getName());
			response.addError(Messages.getCity(CityMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		City city = mapper.map(cityDTO, City.class);
		city = this.cityService.persistir(city);

		response.setData(city);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/edit")
	public ResponseEntity<Response<City>> edit(@Valid @RequestBody EditCityDTO cityDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Editando a cidade: {}", cityDTO.toString());
		Response<City> response = new Response<City>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para edição da cidade: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		Optional<City> cityOpt = this.cityService.findByName(cityDTO.getName());
		if (cityOpt.isPresent()) {
			log.error("Erro ao validar o nome: {}", cityDTO.getName());
			response.addError(Messages.getCity(CityMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		City city = mapper.map(cityDTO, City.class);
		city = this.cityService.persistir(city);

		response.setData(city);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Response<City>> remove(@PathVariable("id") Long id) throws NoSuchAlgorithmException {
		log.info("Removendo a cidade: {}", id);
		Response<City> response = new Response<City>();

		Optional<City> cityOpt = this.cityService.findById(id);
		if (!cityOpt.isPresent()) {
			log.info("A autenticação não foi encontrada para o nome: {}", id);
			response.addError(Messages.getClient(GenericMessage.NONEXISTENT.toString(), id));

			return ResponseEntity.badRequest().body(response);
		}

		this.cityService.deleteById(id);
		cityOpt.get().setClients(Collections.emptyList());

		response.setData(cityOpt.get());
		return ResponseEntity.ok(response);
	}
}
