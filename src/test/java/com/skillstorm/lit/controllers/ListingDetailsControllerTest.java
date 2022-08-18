package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.ListingDetailsService;

@WebMvcTest(ListingDetailsController.class)
public class ListingDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	ListingDetailsService service;

	private ListingDetails details;
	private static UUID id;
	private String url = "/listing-details/v1/";

	@BeforeAll
	public static void setupOnce() {
		id = UUID.fromString("638cc430-511a-490f-93be-48ee957bf731");
	}

	@BeforeEach
	public void setup() {
		details = new ListingDetails();
		details.setId(id);
		details.setDescription("A stuffed mouse cat toy.");
		details.setMaterial("Synthetic fur and cotton stuffing.");
		details.setWeightDescription("4oz");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
	}
	
	@Test
	public void testGetDetailsById() throws Exception {
		Mockito.when(service.findById(any())).thenReturn(details);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url + id)
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(mapper.writeValueAsString(details))
				        .accept(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk())
				 .andExpect(content().string(mapper.writeValueAsString(details)));
		
		verify(service, times(1)).findById(any());
	}
	
	@Test
	public void testGetDetailsByIdNotFound() throws Exception {
		Mockito.when(service.findById(any())).thenReturn(null);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url + id)
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(mapper.writeValueAsString(details))
				        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""));
		
		verify(service, times(1)).findById(any());
	}
	
	@Test
	public void testGetAllDetails() throws Exception {
		List<ListingDetails> listDetails = new ArrayList<>();
		listDetails.add(details);
		Mockito.when(service.findAll()).thenReturn(listDetails);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(mapper.writeValueAsString(listDetails))
				        .accept(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk())
				 .andExpect(content().string(mapper.writeValueAsString(listDetails)));
		
		verify(service, times(1)).findAll();
	}
	
	@Test 
	public void testCreate() throws Exception {
		Mockito.when(service.save(any())).thenReturn(details);
		
		// Details to save
		ListingDetails detailsToSave = new ListingDetails();
		detailsToSave.setDescription(details.getDescription());
		detailsToSave.setMaterial(details.getMaterial());
		detailsToSave.setWeightDescription(details.getWeightDescription());
		detailsToSave.setReleaseDate(details.getReleaseDate());
		
		MockHttpServletResponse response = this.mockMvc
		        .perform(MockMvcRequestBuilders.post(url)
		        		.contentType(MediaType.APPLICATION_JSON)
		        		.content(mapper.writeValueAsString(detailsToSave))
		        		.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isCreated()).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(mapper.writeValueAsString(details));
	}

	@Test
	public void testUpdate() throws Exception {
		details.setDescription("A stuffed bird cat toy.");
		details.setMaterial("Synthetic featherse and cotton stuffing.");
		details.setWeightDescription("3oz");

		Mockito.when(service.update(any(), any())).thenReturn(details);

		String url = this.url + id;

		this.mockMvc
				.perform(MockMvcRequestBuilders.put(url).contentType("application/json")
						.content(mapper.writeValueAsString(details)).accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(details)));

		verify(service, times(1)).update(any(), any());
	}

	@Test
	public void testUpdateWhenIdNotFound() throws Exception {
		// given
		Mockito.when(service.update(details, id)).thenThrow(EntityNotFoundException.class);

		// when
		MockHttpServletResponse response = mockMvc.perform(put(this.url + id).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus() == HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString().isEmpty());
	}

	@Test
	public void testDeleteById() throws Exception {
		Mockito.doNothing().when(service).delete(id);

		String url = "/listing-details/v1/" + id;

		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(MockMvcResultMatchers.status().isOk());

		verify(service, times(1)).delete(id);
	}

	@Test
	public void testDeleteByIdNotFound() throws Exception {
		// given
		Mockito.doThrow(EntityNotFoundException.class).when(service).delete(id);
		;

		// when
		MockHttpServletResponse response = mockMvc.perform(put(this.url + id).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus() == HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString().isEmpty());

	}

}
