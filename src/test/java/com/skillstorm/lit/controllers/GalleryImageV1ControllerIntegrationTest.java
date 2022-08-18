package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;
import com.skillstorm.lit.services.ListingDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class GalleryImageV1ControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private GalleryImageService imageService;
	
	@Autowired
	private ListingDetailsService detailService;
	
	@Test
	public void testFindById() throws Exception {
		// put a image in the database
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setImageSrc("no image source");
		image.setListingDetail(detail);

		Set<GalleryImage> images = new HashSet<>();
		images.add(image);
		detail.setGalleryImages(images);
		
		image = this.imageService.create(image);
		
		// check that it is there using mock GET request
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		// clean up database
		this.imageService.deleteById(image.getId());
		this.detailService.delete(detail.getId());
	}
	
	@Test
	public void testFindAll() throws JsonProcessingException, Exception {

		// put a image in the database
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		GalleryImage image2 = new GalleryImage();
		image.setImageSrc("no image source");
		image2.setImageSrc("image source");
		image.setListingDetail(detail);
		image2.setListingDetail(detail);
		
		image = this.imageService.create(image);
		image2 = this.imageService.create(image2);
		
		List<GalleryImage> expected = new ArrayList<>();
		expected.add(image);
		expected.add(image2);
		
		
		// check that it is there using mock GET request
		String url = "/gallery/v1/";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
		
		// clean up database
		this.imageService.deleteById(image.getId());
		this.imageService.deleteById(image2.getId());
		this.detailService.delete(detail.getId());
	}
	
	@Test 
	public void testFindByListingDetailsId() throws JsonProcessingException, Exception {
		// put a image in the database
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		GalleryImage image2 = new GalleryImage();
		image.setImageSrc("no image source");
		image2.setImageSrc("image source");
		image.setListingDetail(detail);
		image2.setListingDetail(detail);
		
		image = this.imageService.create(image);
		image2 = this.imageService.create(image2);
		
		// check that it is there using mock GET request
		String url = "/gallery/v1/listing-details/" + detail.getId();
		System.out.println(url);
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

		// clean up database
		this.imageService.deleteById(image.getId());
		this.imageService.deleteById(image2.getId());
		this.detailService.delete(detail.getId());
	}
	
	@Test
	public void testCreate() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image.setImageSrc("no image source");
		
		String url = "/gallery/v1/";
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				.andReturn();	
		
		// clean up database
		image = mapper.readValue(result.getResponse().getContentAsString(), GalleryImage.class);
		this.imageService.deleteById(image.getId());
		this.detailService.delete(detail.getId());
	}
	
	@Test
	public void testUpdateImageFound() throws Exception {
		GalleryImage image = new GalleryImage();
		image = this.imageService.create(image);
		image.setImageSrc("image source");
		
		// image should now be different than the one in the db
		GalleryImage foundImage = this.imageService.findById(image.getId());
		assertNotEquals(foundImage.getImageSrc(), image.getImageSrc());
		
		//perform the update
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		// image matches the one in the db after updating
		foundImage = this.imageService.findById(image.getId());
		assertEquals(foundImage.getImageSrc(), image.getImageSrc());
		
		this.imageService.deleteById(image.getId());
	}
	
	@Test
	public void testUpdateImageNotFound() throws Exception {
		GalleryImage image = new GalleryImage();
		UUID id = UUID.fromString("88105708-bce3-4a6a-abec-842b2557c504");
		
		//perform the update
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isBadRequest());
		
		// no image with id in the database
		assertNull(this.imageService.findById(id));
	}
	
	@Test
	public void testDeleteById() throws Exception {
		GalleryImage image = new GalleryImage();
		image = this.imageService.create(image);
		
		// image is not in db
		assertNotNull(this.imageService.findById(image.getId()));
		
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				).andExpect(status().isNoContent());
		
		// image should no longer be in database
		assertNull(this.imageService.findById(image.getId()));
	}
	
	@Test
	public void testDeleteByDetailId() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image = this.imageService.create(image);
		
		GalleryImage image2 = new GalleryImage();
		image2.setListingDetail(image.getListingDetail());
		this.imageService.create(image2);
	
		
		// number of images in db
		int numImages = this.imageService.findAll().size();
		
		// perform delete
		String url = "/gallery/v1/listing-details/" + detail.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				).andExpect(status().isNoContent());
		
		// deleted two images, so should be two less than numImages
		assertTrue(this.imageService.findAll().size() == numImages-2);
	}
}
