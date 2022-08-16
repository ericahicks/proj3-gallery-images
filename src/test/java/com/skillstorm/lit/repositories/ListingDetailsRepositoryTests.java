package com.skillstorm.lit.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.lit.data.ListingDetailsRepository;
import com.skillstorm.lit.models.ListingDetails;

@SpringBootTest
@ActiveProfiles("dev")
public class ListingDetailsRepositoryTests {
	Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ListingDetailsRepository repository;
	
	@Test
	public void getListingDetailsTest() {

        // Checks to see if there is a set repository
        assertNotNull(repository);

        // Calls the repository
        List<ListingDetails> details = (List<ListingDetails>) repository.findAll();
        
        // Assuming the local repository is set up with at least 1 details row in it currently
        assertThat(details).isNotEmpty();
	}
	
	@Test
	public void saveListingDetailsTest() {
		// Create a new listing details object
		ListingDetails details = new ListingDetails();
		details.setDescription("A stuffed mouse cat toy.");
		details.setMaterial("Synthetic fur and cotton stuffing.");
		details.setWeightDescription("4oz");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		
		// save the listing object to the database
		details = repository.save(details);
		
		// check that the listing details id was set
		LOG.debug("Test details created with id of " + details.getId());
		Assertions.assertThat(details.getId()).isNotNull();
	}
	
//	NOT WORKING
//	@Test
//	@Transactional
//	public void getListingDetailsByIdTest() {
//		// Create a new listing details object
//		ListingDetails details = new ListingDetails();
//		details.setDescription("A stuffed llama cat toy.");
//		details.setMaterial("Synthetic wool and cotton stuffing.");
//		details.setWeightDescription("6oz");
//		details.setReleaseDate(new Date(System.currentTimeMillis()));
//		
//		// save the listing object to the database
//		details = repository.save(details);
//		UUID id = details.getId();
//		// check that the listing details id was set
//		LOG.debug("Test details created with id of " + details.getId());
//		Assertions.assertThat(id).isNotNull();
//		
//		// find by id
//		Optional<ListingDetails> these = repository.findById(id);
//		
//		assertTrue(these == null ? false : these.get().isSame(details));
//	}

	@Test
	public void getListingDetailsUpdateByIdTest() {
		// Create a new listing details object
		ListingDetails details = new ListingDetails();
		details.setDescription("A stuffed fish cat toy.");
		details.setMaterial("Synthetic cloth and cotton stuffing.");
		details.setWeightDescription("3oz");
		details.setReleaseDate(new Date(System.currentTimeMillis()));
		
		// save the listing object to the database
		details = repository.save(details);
		UUID id = details.getId();
		
		// check that the listing details id was set
		LOG.debug("Test details created with id of " + details.getId());
		Assertions.assertThat(id).isNotNull();
		
		// update by id
		details.setDescription("A stuffed bird cat toy.");
		details.setMaterial("Synthetic feathers and cotton stuffing");
		ListingDetails these = repository.save(details);
		
		assertEquals("A stuffed bird cat toy.", these.getDescription());

		assertEquals("Synthetic feathers and cotton stuffing", these.getMaterial());
		
	}

}
