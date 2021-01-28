package com.compasso.uol.gabriel.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.compasso.uol.gabriel.dto.EditCityDTO;
import com.compasso.uol.gabriel.dto.IncludeCityDTO;
import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.response.Error;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.utils.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper objMapper;

	@MockBean
	private CityService cityService;

	@Mock
	private City city;

	@Mock
	private ReturnCityDTO returnCity;

	private final String BASE_URL = "/city";

	private static final Long ID = 1L;
	private static final String NAME = "Juiz de Fora";
	private static final String STATE = "Minas Gerais";

	@BeforeEach
	public void setup() {
		city = new City();
		city.setName(NAME);
		city.setState(STATE);

		returnCity = new ReturnCityDTO();
		returnCity.setName(NAME);
		returnCity.setState(STATE);
	}

	@Test
	@WithMockUser
	public void find_all_cities() throws Exception {
		List<ReturnCityDTO> cities = new ArrayList<ReturnCityDTO>();
		cities.add(returnCity);

		when(this.cityService.findAll()).thenReturn(cities);

		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void find_city_by_state_with_invalid_state() throws Exception {
		when(this.cityService.findByState(STATE)).thenReturn(Optional.of(city));

		city.setName("Não é a cidade correta.");
		Error error = Messages.getCity(CityMessage.NONEXISTENT.toString());

		final String URL = String.format("%s?state=%s", BASE_URL, city.getName());
		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].title", equalTo(error.getTitle())))
				.andExpect(jsonPath("$.errors[0].text", equalTo(error.getText())));
	}

	@Test
	@WithMockUser
	public void find_city_by_name_with_invalid_name() throws Exception {
		when(this.cityService.findByName(NAME)).thenReturn(Optional.of(city));

		city.setName("Não é a cidade correta.");
		Error error = Messages.getCity(CityMessage.NONEXISTENT.toString());

		final String URL = String.format("%s?name=%s", BASE_URL, city.getName());
		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].title", equalTo(error.getTitle())))
				.andExpect(jsonPath("$.errors[0].text", equalTo(error.getText())));
	}

	@Test
	@WithMockUser
	public void find_city_options() throws Exception {
		OptionDTO<Long> option = new OptionDTO<Long>();
		option.setValue(ID);
		option.setText(NAME);

		List<OptionDTO<Long>> options = new ArrayList<OptionDTO<Long>>();
		options.add(option);

		when(this.cityService.findOptions()).thenReturn(options);

		final String URL = BASE_URL + "/options";
		mockMvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].text", equalTo(option.getText())))
				.andExpect(jsonPath("$.data[0].value", equalTo(1))).andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void include_city() throws Exception {
		when(this.cityService.persist(city)).thenReturn(city);

		IncludeCityDTO includeCity = mapper.map(city, IncludeCityDTO.class);
		mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).content(objMapper.writeValueAsString(includeCity))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void edit_city() throws Exception {
		city.setId(ID);
		EditCityDTO editCity = mapper.map(city, EditCityDTO.class);

		when(this.cityService.persist(city)).thenReturn(city);
		when(this.cityService.findById(ID)).thenReturn(Optional.of(city));

		mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(objMapper.writeValueAsString(editCity))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void delete_city() throws Exception {
		city.setId(ID);
		when(this.cityService.findById(ID)).thenReturn(Optional.of(city));

		final String URL = String.format("%s?id=%s", BASE_URL, city.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete(URL)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", equalTo(NAME))).andExpect(jsonPath("$.data.state", equalTo(STATE)))
				.andExpect(jsonPath("$.errors").isEmpty());

		verify(this.cityService, times(1)).deleteById(ID);
	}
}
