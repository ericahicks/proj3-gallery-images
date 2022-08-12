package com.skillstorm.lit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.controllers.GalleryImageV1Controller;
import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Service
@Transactional
public class GalleryImageServiceImpl implements GalleryImageService {
	private static final Logger LOG = LoggerFactory.getLogger(GalleryImageV1Controller.class);
	@Autowired
	GalleryImageRepository repository;

	@Override
	public List<GalleryImage> findAll() {
		Iterable<GalleryImage> itr = repository.findAll();
		List<GalleryImage> list = new ArrayList<>();
		for (GalleryImage image : itr) {
			list.add(image);
		}
		return list;
	}
	
	@Override
	public List<GalleryImage> findAllGalleryImage() {
		return repository.findAllGalleryImage();
	}
	
	@Override
	public
	List<ListingDetails> findAllListingDetailsD() {
		return repository.findAllListingDetailsD();
	}

	@Override
	public List<GalleryImage> findByListingDetailsId(UUID id) {
		return repository.findByListingDetail(id);
	}

	@Override
	public GalleryImage findById(UUID id) {
		Optional<GalleryImage> image = repository.findById(id);
		return image.isPresent() ? image.get() : null;
 	}

	@Override
	public GalleryImage create(GalleryImage image) {
		return repository.save(image);
	}
	
	@Override
	public GalleryImage update(GalleryImage galleryImage) {
		if (repository.findById(galleryImage.getId()).isPresent() ) {
			return repository.save(galleryImage); 
	    } else {
	    	throw new EntityNotFoundException();
	    }
	}

	@Override
	public void deleteById(UUID id) {
		if (repository.findById(id).isPresent() ) {
			repository.deleteById(id);   
	    } else {
	    	throw new EntityNotFoundException();
	    }
		
	}

	@Override
	public void deleteAllFromDetails(ListingDetails listingDetail) {
		if (repository.findById(listingDetail.getId()).isPresent() ) {
			repository.deleteByListingDetail(listingDetail);   
	    } else {
	    	throw new EntityNotFoundException();
	    }
	}

}
