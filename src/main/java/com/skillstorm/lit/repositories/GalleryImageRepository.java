package com.skillstorm.lit.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.skillstorm.lit.models.GalleryImage;

public interface GalleryImageRepository extends CrudRepository<GalleryImage, UUID> {

}
