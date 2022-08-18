package com.skillstorm.lit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Service
public class GalleryImageServiceImpl implements GalleryImageService {
	private static final Logger LOG = LoggerFactory.getLogger(GalleryImageServiceImpl.class);

	@Autowired
	GalleryImageRepository repository;
	
//	@Autowired
//	private ImageStorageService imageStorageService;
	
	@Override
	public List<GalleryImage> findAll() {
		return (List<GalleryImage>) repository.findAll();
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
		Optional<GalleryImage> image = repository.findById(galleryImage.getId());
		return image.isPresent() ? repository.save(galleryImage) : null;
	}

	@Override
	public void deleteById(UUID id) {
		Optional<GalleryImage> image = repository.findById(id);
		if (image.isPresent()) {
			repository.deleteById(id);
//			imageStorageService.deleteImage(fileName);
		} 
	}

	@Override
	public void deleteAllFromDetails(ListingDetails listingDetail) {
		List<GalleryImage> imageList = this.findByListingDetailsId(listingDetail.getId());
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