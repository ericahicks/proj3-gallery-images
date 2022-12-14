package com.skillstorm.lit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class GalleryImageV1ControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	GalleryImageService service;
	
	@Test
	public void testGetImage() throws JsonProcessingException, Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		Mockito.when(service.findById(any())).thenReturn(image);
		
		String url = "/gallery/v1/" + id.toString();
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		verify(service, times(1)).findById(any());
	}
	
	@Test
	public void testGetAll() throws JsonProcessingException, Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		List<GalleryImage> images = new ArrayList<>();
		images.add(image);
		
		Mockito.when(service.findAll()).thenReturn(images);
		
		String url = "/gallery/v1/";
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				).andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(images)));
		
		verify(service, times(1)).findAll();
	}
	
	@Test
	public void testGetImageByListingDetail() throws JsonProcessingException, Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		List<GalleryImage> images = new ArrayList<>();
		images.add(image);
		
		Mockito.when(service.findByListingDetailsId(any())).thenReturn(images);
		
		String url = "/gallery/v1/listing-details/" + id.toString();
		this.mockMvc.perform(MockMvcRequestBuilders.get(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt()))
				).andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(images)));
		
		verify(service, times(1)).findByListingDetailsId(any());
	}
	
	@Test
	public void testCreateImageDetailsFound() throws JsonProcessingException, Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		Mockito.when(service.create(any())).thenReturn(image);
		
		String url = "/gallery/v1/";
		this.mockMvc.perform(MockMvcRequestBuilders.post(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		verify(service, times(1)).create(any());
		
	}
	
	@Test
	public void testUpdateImageFound() throws Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		Mockito.when(service.update(any())).thenReturn(image);
		
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		verify(service, times(1)).update(any());
	}
	
	@Test
	public void testUpdateImageNotFound() throws Exception {
		UUID id = UUID.randomUUID();
		GalleryImage image = new GalleryImage();
		image.setId(id);
		
		Mockito.when(service.update(any())).thenReturn(null);
		
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isBadRequest());
		
		verify(service, times(1)).update(any());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		UUID id = UUID.randomUUID();
		
		Mockito.doNothing().when(service).deleteById(id);
		
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt())
				).andExpect(status().isNoContent());
		
		verify(service, times(1)).deleteById(id);
	}
	
	@Test
	public void testDeleteByDetailId() throws Exception {
		UUID id = UUID.randomUUID();
		ListingDetails detail = new ListingDetails();
		detail.setId(id);
		
		Mockito.doNothing().when(service).deleteAllFromDetails(detail);
		
		String url = "/gallery/v1/listing-details/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
//				.with(SecurityMockMvcRequestPostProcessors.jwt()
				).andExpect(status().isNoContent());
		
		verify(service, times(1)).deleteAllFromDetails(any());
	}
}