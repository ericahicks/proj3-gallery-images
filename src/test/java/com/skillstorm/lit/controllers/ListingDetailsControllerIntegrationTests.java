package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.UUID;

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

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	private static String url;
//	private ListingDetails details;
//	private UUID id;
//	
//	@BeforeAll
//	public static void init() {
//		url =  "/listing-details/v1/";
//	}
//	
//	@BeforeEach
//	public void setup() {
//		id = UUID.randomUUID();
//		details = new ListingDetails();
//		details.setId(id);
//		details.setDescription("A stuffed mouse cat toy.");
//		details.setMaterial("Synthetic fur and cotton stuffing.");
//		details.setWeightDescription("4oz");
//		details.setReleaseDate(new Date(System.currentTimeMillis()));
//	}
//	
	@Test 
	public void testUpdateDetailsFound() throws Exception {

		///////////////////////////////////////////////////////////////////////
		//////////////////////////// TRY #2 ///////////////////////////////////
//		ListingDetails details = new ListingDetails();
//		details = this.service.save(details);
//		details.setDescription("Stuffed dinosaur cat toy");
//		
//		// details should now be different than the one in the db
//		ListingDetails foundDetails = this.service.findById(details.getId());
//		assertNotEquals(foundDetails.getDescription(), details.getDescription());
//		
//		//perform the update
//		String url = "/gallery/v1/" + details.getId();
//		this.mockMvc.perform(MockMvcRequestBuilders.put(url + details.getId().toString())
//				.contentType("application/json")
//	            .content(mapper.writeValueAsString(details))
//	            .accept("application/json"))
//				.andExpect(status().isOk())
//				.andExpect(content().string(mapper.writeValueAsString(details)));
//		
//		// image matches the one in the db after updating
//		foundDetails = this.service.findById(details.getId());
//		assertEquals(foundDetails.getDescription(), details.getDescription());
//		
//		this.service.delete(details.getId());
		
		/////////////////////////////////////////////////////////////
		///////////////////// TRY #1 ////////////////////////////////
		// create details
//		ListingDetails savedDetails = service.save(details);
//		
//		// update details
//		details.setDescription("A stuffed bird cat toy.");
//		details.setMaterial("Synthetic featherse and cotton stuffing.");
//		details.setWeightDescription("3oz");
//		
//		// details should now be different than the one in the db
//		ListingDetails foundDetails = service.findById(savedDetails.getId());
//		assertNotEquals(foundDetails.getDescription(), details.getDescription());
//		assertNotEquals(foundDetails.getMaterial(), details.getMaterial());
//		assertNotEquals(foundDetails.getWeightDescription(), details.getWeightDescription());
//		
//		// perform update
//		details.setId(savedDetails.getId());
//		mockMvc.perform(MockMvcRequestBuilders.put(url + savedDetails.getId())
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(mapper.writeValueAsString(details))
//					.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(content().string(mapper.writeValueAsString(details)));
//		
//		// image matches the one in the db after udating
//		foundDetails = service.findById(savedDetails.getId());
//		assertThat(foundDetails).usingRecursiveComparison().isEqualTo(savedDetails);
//		
		// clean up the db
//		service.delete(savedDetails.getId());
	}
	
	

}
