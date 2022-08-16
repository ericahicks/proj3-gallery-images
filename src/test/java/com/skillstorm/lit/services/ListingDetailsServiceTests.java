package com.skillstorm.lit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.skillstorm.lit.data.ListingDetailsRepository;
import com.skillstorm.lit.models.ListingDetails;

@SpringBootTest
@ActiveProfiles("dev")
public class ListingDetailsServiceTests {
	
	@InjectMocks
	ListingDetailsServiceImpl service;
	
	@Mock
	ListingDetailsRepository repository;
	
	private ListingDetails details;
	private static UUID id;
	
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
	public void testUpdateDetailsFound() {
		details.setDescription("A stuffed fish cat toy.");
		details.setMaterial("Synthetic cloth and cotton stuffing.");
		details.setWeightDescription("3oz");
		
		Mockito.when(repository.save(any())).thenReturn(details);
		Mockito.when(repository.findById(any())).thenReturn(Optional.of(details));
		
		ListingDetails updatedDetails = service.update(details, details.getId());
		
		assertEquals(details.getId(), updatedDetails.getId());
		assertEquals(details.getDescription(), updatedDetails.getDescription());
		assertEquals(details.getMaterial(), updatedDetails.getMaterial());
		assertEquals(details.getWeightDescription(), updatedDetails.getWeightDescription());
		
		verify(repository, times(1)).save(details);
		verify(repository, times(1)).findById(details.getId());
	}
	
	@Test
	public void testUpdateDetailsNotFound() {
		details.setDescription("A stuffed fish cat toy.");
		// represents a details object that is not in the database
		
		Mockito.when(repository.save(any())).thenReturn(details);
		
		assertThrows(EntityNotFoundException.class, () -> {
			service.update(details, details.getId());
		});
		

		verify(repository, times(0)).save(details);
		verify(repository, times(1)).findById(details.getId());
	}
	
	@Test
	public void testDeleteByIdFound() {
		Mockito.doNothing().when(repository).deleteById(details.getId());
		Mockito.when(repository.findById(details.getId())).thenReturn(Optional.of(details));
		
		service.delete(details.getId());
		
		verify(repository, times(1)).deleteById(details.getId());
		verify(repository, times(1)).findById(details.getId());
	}
	
	@Test
	public void testDeleteByIdNotFound() {
		Mockito.doNothing().when(repository).deleteById(details.getId());
		// Why don't you have to mock the .findById here?
		
		assertThrows(EntityNotFoundException.class, () -> {
			service.delete(details.getId());
		});
		
		verify(repository, times(0)).deleteById(details.getId());
		verify(repository, times(1)).findById(details.getId());
	
	}

}
