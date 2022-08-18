package com.skillstorm.lit.services;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.ListingRepository;
import com.skillstorm.lit.models.Listing;

@Service
public class ListingService {

	private static final Logger log = LoggerFactory.getLogger(ListingService.class);

	@Autowired
	private ListingRepository listingRepository;
	
	/**
	 * Locale is determined by the Accept-Language header have a valid value: such
	 * as "en-US"
	 * 
	 * @param locale
	 * @return
	 */ 

	public List<Listing> findAll() {
		return this.listingRepository.findAll();
	}

	/**
	 * 
	 * Represents listing request object (modeled in Listing.java in models package)
	 * 
	 * @param listing
	 * @return
	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Listing add(Listing listing) {
		Listing dbListing = listingRepository.save(listing);
		return new Listing(dbListing);
	}

	public Listing findById(UUID id) {
        log.debug("===============================");
        log.debug("GETTING SPECIFIC LISTING WITH ID: " + id);
        Listing product = this.listingRepository.findListingById(id);
        return product;
    }
	
}
