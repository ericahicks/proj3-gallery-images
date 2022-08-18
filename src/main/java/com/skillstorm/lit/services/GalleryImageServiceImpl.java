package com.skillstorm.lit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Service
@Transactional
public class GalleryImageServiceImpl implements GalleryImageService {
	private static final Logger LOG = LoggerFactory.getLogger(GalleryImageServiceImpl.class);

	@Autowired
	GalleryImageRepository repository;

	@Override
	public List<GalleryImage> findAll() {
		return (List<GalleryImage>) repository.findAll();
	}

	@Override
	public List<GalleryImage> findByListingDetailsId(UUID id) {
		return repository.findByListingDetail(id);
	}
	
	@Override
	public List<GalleryImage> findByListingDetail(ListingDetails listingDetail) {
		return repository.findByListingDetail(listingDetail);
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
		if (repository.findById(galleryImage.getId()).isPresent()) {
			return repository.save(galleryImage);
		} else {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public void deleteById(UUID id) {
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
		} else {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public void deleteAllFromDetails(ListingDetails listingDetail) {
		List<GalleryImage> imageList = this.findByListingDetail(listingDetail);
		repository.deleteByListingDetail(listingDetail);
		for (GalleryImage image : imageList) {
//			imageStorageService.deleteImage(fileName);
		}
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

}
