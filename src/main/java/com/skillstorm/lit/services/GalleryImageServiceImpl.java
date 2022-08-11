package com.skillstorm.lit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.lit.controllers.GalleryImageV1Controller;
import com.skillstorm.lit.data.GalleryImageRepository;
import com.skillstorm.lit.models.GalleryImage;
import com.skillstorm.lit.models.ListingDetails;

@Service
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
	public List<GalleryImage> findByListingDetailsId(UUID id) {
		return repository.findByListingDetail(id);
	}

	@Override
	public GalleryImage findById(String id) {
		Optional<GalleryImage> image = repository.findById(id);
		return image.isPresent() ? image.get() : null;
 	}

	@Override
	public GalleryImage create(GalleryImage image) {
		return repository.save(image);
	}

}
