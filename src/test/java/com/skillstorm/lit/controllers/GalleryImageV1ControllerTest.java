package com.skillstorm.lit.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;
import com.skillstorm.lit.services.GalleryImageService;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryImageV1ControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	GalleryImageService service;
	
	@Test
	public void testUpdate() throws Exception {
		UUID id = UUID.fromString("638cc430-511a-490f-93be-48ee957bf731");
		GalleryImage image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(id);
		
		Mockito.when(service.update(any())).thenReturn(image);
		
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.put(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(image))
	            .accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(image)));
		
		verify(service, times(1)).update(any());
	}
	
	@Test
	public void testDeleteById() throws Exception {
		UUID id = UUID.fromString("638cc430-511a-490f-93be-48ee957bf731");
		
		Mockito.doNothing().when(service).deleteById(id);
		
		String url = "/gallery/v1/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
				.andExpect(status().isOk());
		
		verify(service, times(1)).deleteById(id);
	}
	
	@Test
	public void testDeleteAllFromDetails() throws Exception {
		UUID id = UUID.fromString("638cc430-511a-490f-93be-48ee957bf731");
		ListingDetails detail = new ListingDetails();
		detail.setId(id);
		
		Mockito.doNothing().when(service).deleteAllFromDetails(detail);
		
		String url = "/gallery/v1/";
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.contentType("application/json")
	            .content(mapper.writeValueAsString(detail)))
				.andExpect(status().isOk());
		
		verify(service, times(1)).deleteAllFromDetails(any());
	}
	
	@Test
	public void testDeleteByDetailId() throws Exception {
		UUID id = UUID.fromString("638cc430-511a-490f-93be-48ee957bf731");
		ListingDetails detail = new ListingDetails();
		detail.setId(id);
		
		Mockito.doNothing().when(service).deleteAllFromDetails(detail);
		
		String url = "/gallery/v1//listing-details/" + id;
		this.mockMvc.perform(MockMvcRequestBuilders.delete(url))
				.andExpect(status().isOk());
		
		verify(service, times(1)).deleteAllFromDetails(any());
	}
}