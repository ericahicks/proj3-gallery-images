package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.HashSet;
import java.util.UUID;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.ListingDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ListingDetailsControllerIntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private ListingDetailsService service;
	
	private static String url = "/listing-details/v1/";
	
	@Test
	public void testFindById() throws JsonProcessingException, Exception {
		// put details in database to find by id
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		details.setGalleryImages(new HashSet<GalleryImage>());
		details = service.save(details);
		
		// find by id using mock GET request
		this.mockMvc.perform(MockMvcRequestBuilders.get(url + details.getId())
				.accept("application/json"))
					.andExpect(status().isOk())
					.andExpect(content().string(mapper.writeValueAsString(details)));
		
		// clean up database
		service.delete(details.getId());
	}
	
	@Test
	public void testFindAll() {
		
	}
	
	@Test
	public void testCreate() {
		
	}
	
	@Test
	public void testUpdate() {
		
	}
	
	@Test
	public void testDelete() {
		
	}
	
	@Test
	public void testDeleteWithImages() {
		
	}

}
