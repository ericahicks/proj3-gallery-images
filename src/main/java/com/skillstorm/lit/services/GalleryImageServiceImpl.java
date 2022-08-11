package com.skillstorm.lit.services;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GalleryImage> findByListingDetailsId() {
		return repository.findByListingDetail();
	}

	@Override
	public GalleryImage findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GalleryImage create() {
		// TODO Auto-generated method stub
		return null;
	}

}
