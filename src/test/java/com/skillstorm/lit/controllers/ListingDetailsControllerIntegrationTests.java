package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.data.ListingDetailsRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;
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
	
	@Autowired
	private ListingDetailsRepository repository;
	
	@Autowired
	private GalleryImageService imageService;
	
	@Autowired
	private GalleryImageRepository imageRepository;
	
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
	public void testFindAll() throws JsonProcessingException, Exception {
		// put details in database to find by id
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		details.setGalleryImages(new HashSet<GalleryImage>());
		details = service.save(details);
		
		ListingDetails details2 = new ListingDetails();
		details2.setDescription("Park bench made from plastic.");
		details2.setMaterial("plastic");
		details2.setReleaseDate(new Date(System.currentTimeMillis()));
		details2.setGalleryImages(new HashSet<GalleryImage>());
		details2 = service.save(details2);
		
		// Expected list to be returned
		List<ListingDetails> expected = new ArrayList<>();
		expected.add(details);
		expected.add(details2);
		
		// find all using mock GET request
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept("application/json"))
					.andExpect(status().isOk())
					.andExpect(content().string(mapper.writeValueAsString(expected)));
		long count = repository.count();
		assertThat(count).isEqualTo(2);
		
		// clean up database
		service.delete(details.getId());
		service.delete(details2.getId());
	}
	
	@Test
	public void testCreate() throws JsonProcessingException, Exception {
		// create details to put in database
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
//				details.setReleaseDate(new Date(System.currentTimeMillis()));
		details.setGalleryImages(new HashSet<GalleryImage>());
		
		// create using POST mock request
		MockHttpServletResponse response =
				this.mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(details))
				.accept("application/json"))
					.andExpect(status().isCreated())
					.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
					.andReturn().getResponse();
		
		ListingDetails returned = mapper.readValue(response.getContentAsString(), ListingDetails.class);
		assertThat(returned)
			    .usingRecursiveComparison()
			    .ignoringExpectedNullFields()
			    .isEqualTo(details);
		
		// clean up database
		service.delete(returned.getId());
		
	}
	
	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		// put details in database to try updating
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
//		details.setReleaseDate(new Date(System.currentTimeMillis()));
		details.setGalleryImages(new HashSet<GalleryImage>());
		details = service.save(details);
		
		// make local change to details 
		details.setMaterial("repurposed wood");
		
		// update using PUT mock request
		MockHttpServletResponse response =
				this.mockMvc.perform(MockMvcRequestBuilders.put(url + details.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(details))
				.accept("application/json"))
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(details.getId().toString()))
					.andReturn().getResponse();
		
		ListingDetails returned = mapper.readValue(response.getContentAsString(), ListingDetails.class);
		assertThat(returned)
			    .usingRecursiveComparison()
			    .ignoringExpectedNullFields()
			    .isEqualTo(details);
		
		// clean up database
		service.delete(returned.getId());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		// put details in database to try deleting
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		details.setGalleryImages(new HashSet<GalleryImage>());
		details = service.save(details);
		
		// delete using DELETE mock request
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url + details.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(details))
				.accept("application/json"))
				.andExpect(status().isNoContent());
		long count = repository.count();
		assertThat(count).isEqualTo(0);
		// check that the details are gone from the database
		ListingDetails found = service.findById(details.getId());
		assertThat(found).isNull();
	}
	
	@Test
	public void testDeleteWithImages() throws JsonProcessingException, Exception {
		// put details in database to try deleting
		ListingDetails details = new ListingDetails();
		details.setDescription("Park bench made from repurposed wood.");
		details.setMaterial("wood");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		Set<GalleryImage> images = new HashSet<>();
		details.setGalleryImages(images);
		details = service.save(details);
		
		// Create GalleryImages that reference details that must be deleted when details is
		GalleryImage image = new GalleryImage();
		image.setImageSrc("some image");
		image.setListingDetail(details);
		GalleryImage image2 = new GalleryImage();
		image2.setImageSrc("the most beautiful park bench you've ever seen in your life");
		image2.setListingDetail(details);
		
		images.add(image);
		images.add(image2);
		
		image = imageService.create(image);
		image2 = imageService.create(image2);
		
		// delete using DELETE mock request
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url + details.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(details))
				.accept("application/json"))
				.andExpect(status().isNoContent());

		// check that the details are gone from the database
		long count = repository.count();
		assertThat(count).isEqualTo(0);

		ListingDetails found = service.findById(details.getId());
		assertThat(found).isNull();
		
		// check that images are gone from the database
		long imageCount = imageRepository.count();
		assertThat(imageCount).isEqualTo(0);
		
		GalleryImage foundImage = imageService.findById(image.getId());
		assertThat(foundImage).isNull();
		foundImage = imageService.findById(image2.getId());
		assertThat(foundImage).isNull();
	}

}
