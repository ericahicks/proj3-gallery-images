package com.skillstorm.lit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		//return repository.findByListingDetail(id);
		return null;
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
		return repository.save(galleryImage);
	}

	@Override
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteAllFromDetails(ListingDetails listingDetail) {
		repository.deleteByListingDetail(listingDetail);
	}

}
