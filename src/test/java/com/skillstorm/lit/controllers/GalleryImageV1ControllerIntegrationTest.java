package com.skillstorm.lit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
	public void testUpdate() throws Exception {
		GalleryImage image = new GalleryImage();
		image = this.imageService.create(image);
		image.setImageSrc("image source");
		
		// image should now be different than the one in the db
		GalleryImage foundImage = this.imageService.findById(image.getId());
		assertNotEquals(foundImage.getImageSrc(), image.getImageSrc());
		
		//perform the update
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		// image matches the one in the db after updating
		foundImage = this.imageService.findById(image.getId());
		assertEquals(foundImage.getImageSrc(), image.getImageSrc());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		GalleryImage image = new GalleryImage();
		image = this.imageService.create(image);
		
		// image is in db
		assertNotNull(this.imageService.findById(image.getId()));
		
		String url = "/gallery/v1/" + image.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
				.andExpect(status().isOk());
		
		// image should no longer be in database
		assertNull(this.imageService.findById(image.getId()));
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
		assertTrue(this.imageService.findAll().size() == 2);
		
		// perform delete
		String url = "/gallery/v1/listing-details/" + detail.getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
			.andExpect(status().isOk());
		
		// should be no images in db after delete
		assertTrue(this.imageService.findAll().size() == 0);
	}
}
