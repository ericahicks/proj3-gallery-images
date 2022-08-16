package com.skillstorm.lit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;


@SpringBootTest
@ActiveProfiles("dev")
public class GalleryImageServiceTests {

	@InjectMocks
	GalleryImageServiceImpl service;
	
	@Mock
	GalleryImageRepository repository;
	
	private GalleryImage image;
	
	
	@BeforeEach
	public void setup() {
		image = new GalleryImage();
		image.setImageSrc("image source");
		image.setId(UUID.fromString("638cc430-511a-490f-93be-48ee957bf731"));
		
		ListingDetails detail = new ListingDetails();
		detail.setId(UUID.fromString("638cc430-511a-490f-93be-48ee957bf890"));
		image.setListingDetail(detail);
	}
	
	
	@Test
	public void testUpdateImageFound() {
		image.setImageSrc("new image source");
		
		Mockito.when(repository.save(any())).thenReturn(image);
		Mockito.when(repository.findById(image.getId())).thenReturn(Optional.of(image));
		
		GalleryImage updatedImage = service.update(image);
		
		assertEquals(image.getId(), updatedImage.getId());
		assertEquals(updatedImage.getImageSrc(), "new image source");
		verify(repository, times(1)).save(image);
		verify(repository, times(1)).findById(image.getId());
	}
	
	@Test
	public void testUpdateImageNotFound() {
		image.setImageSrc("new image source");
		
		Mockito.when(repository.save(any())).thenReturn(image);
		
		assertThrows(EntityNotFoundException.class, () -> {
			service.update(image);
	    });
		
		verify(repository, times(0)).save(image);
		verify(repository, times(1)).findById(image.getId());
	}
	

	@Test
	public void testDeleteByIdFound() {
		Mockito.doNothing().when(repository).deleteById(image.getId());
		Mockito.when(repository.findById(image.getId())).thenReturn(Optional.of(image));
		
		service.deleteById(image.getId());
		
		verify(repository, times(1)).deleteById(image.getId());
		verify(repository, times(1)).findById(image.getId());
	}
	
	@Test
	public void testDeleteByIdNotFound() {
		Mockito.doNothing().when(repository).deleteById(image.getId());
		
		assertThrows(EntityNotFoundException.class, () -> {
			service.update(image);
	    });
		
		verify(repository, times(0)).deleteById(image.getId());
		verify(repository, times(1)).findById(image.getId());
	}
	
	
	@Test
	public void testDeleteAllFromDetails() {
		GalleryImage image2 = new GalleryImage();
		image2.setListingDetail(image.getListingDetail());
		ListingDetails detail = image.getListingDetail();
		
		List<GalleryImage> imageList = Arrays.asList(image, image2);

		Mockito.doNothing().when(repository).deleteByListingDetail(detail);
		Mockito.when(repository.findByListingDetail(detail)).thenReturn(imageList);
		
		service.deleteAllFromDetails(detail);
		
		verify(repository, times(1)).deleteByListingDetail(detail);
		verify(repository, times(1)).findByListingDetail(detail);
		
	}
}