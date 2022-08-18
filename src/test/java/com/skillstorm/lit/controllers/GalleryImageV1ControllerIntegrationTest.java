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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
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
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		

		imageService.deleteById(image.getId());
		detailService.delete(detail.getId());
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
		Set<GalleryImage> images = new HashSet<>();
		images.add(image);
		images.add(image2);
		
		image.getListingDetail().setGalleryImages(images);
		image2.getListingDetail().setGalleryImages(images);
		
		List<GalleryImage> expected = new ArrayList<>();
		expected.add(image);
		expected.add(image2);
		
		// check that it is there using mock GET request
		String url = "/gallery/v1/";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
            .accept("application/json"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
			.andExpect(content().string(mapper.writeValueAsString(expected)));

		imageService.deleteById(image.getId());
		imageService.deleteById(image2.getId());
		detailService.delete(detail.getId());
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
		Set<GalleryImage> images = new HashSet<>();
		images.add(image);
		images.add(image2);
		
		image.getListingDetail().setGalleryImages(images);
		image2.getListingDetail().setGalleryImages(images);
		
		
		// check that it is there using mock GET request
		String url = "/gallery/v1/" + detail.getId();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
            .accept("application/json"))
			.andExpect(status().isOk());
//					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//					.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
//					.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
//					.andExpect(content().string(mapper.writeValueAsString(images)))

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
		imageService.deleteById(image2.getId());
	}
	
	@Test
	public void testCreate() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image.setImageSrc("no image source");
		
		String url = "/gallery/v1/";
		this.mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", UUID.class).isNotEmpty());
		// clean up database

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
	}
	
	@Test
	public void testUpdate() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image.setImageSrc("no image source");
		image = this.imageService.create(image);
		image.setImageSrc("image source");
		
		Set<GalleryImage> images = new HashSet<>();
		images.add(image);
		detail.setGalleryImages(images);
		
		// image should now be different than the one in the db
		GalleryImage foundImage = this.imageService.findById(image.getId());
		assertNotEquals(foundImage.getImageSrc(), image.getImageSrc());
		
		//perform the update
		String url = "/gallery/v1/" + image.getId();
		MockHttpServletResponse response =
				this.mockMvc.perform(MockMvcRequestBuilders.put(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
		    .isEqualTo(mapper.writeValueAsString(image));
		
		// image matches the one in the db after updating
		foundImage = this.imageService.findById(image.getId());
		assertEquals(foundImage.getImageSrc(), image.getImageSrc());
		

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image.setImageSrc("image source");
		image = this.imageService.create(image);
		
		// image is in db
		assertNotNull(this.imageService.findById(image.getId()));
		
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
				.andExpect(status().isOk());
		
		// image should no longer be in database
		assertNull(this.imageService.findById(image.getId()));
		

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
	}
	
	
	@Test
	public void testDeleteAllFromDetails() throws Exception {
		ListingDetails detail = new ListingDetails();
		detail = this.detailService.save(detail);
		
		GalleryImage image = new GalleryImage();
		image.setListingDetail(detail);
		image = this.imageService.create(image);
		
		GalleryImage image2 = new GalleryImage();
		image2.setListingDetail(image.getListingDetail());
		this.imageService.create(image2);

		
		// should be two images in db
		assertTrue(this.imageService.findAll().size() == 2);
		
		// perform delete
		String url = "/gallery/v1";
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(detail)))
				.andExpect(status().isOk());
		
		// should be no images in db after delete
		assertTrue(this.imageService.findAll().size() == 0);
		

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
		imageService.deleteById(image2.getId());
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

		
		// should be two images in db
		assertThat(this.imageService.findAll()).hasSize(2);
		
		// perform delete
		String url = "/gallery/v1/listing-details/" + detail.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
			.andExpect(status().isOk());
		
		// should be no images in db after delete
		assertTrue(this.imageService.findAll().size() == 0);

		detailService.delete(detail.getId());
		imageService.deleteById(image.getId());
		imageService.deleteById(image2.getId());
	}
}
