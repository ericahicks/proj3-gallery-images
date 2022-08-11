package com.skillstorm.lit.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;

@Service
public class GalleryImageServiceImpl implements GalleryImageService {
	
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
	public GalleryImage findById(UUID id) {
		Optional<GalleryImage> image = repository.findById(id);
		return image.isPresent() ? image.get() : null;
 	}

	@Override
	public GalleryImage create(GalleryImage image) {
		return repository.save(image);
	}

}
