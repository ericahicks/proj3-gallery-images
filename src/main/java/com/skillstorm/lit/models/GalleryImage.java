package com.skillstorm.lit.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "LISTING_GALLERY_IMAGES")
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Gallery Image")
public class GalleryImage {

	@Id
	@GeneratedValue(generator = "LISTING_GALLERY_IMAGE_UUID")
	@GenericGenerator(name = "LISTING_GALLERY_IMAGE_UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "LISTING_GALLERY_IMAGE_ID")
	@Type(type = "uuid-char")
	@Schema(description = "Image Id")
	private UUID id;

	@Column(name = "IMAGE_SRC")
	@Schema(description = "Image relative filepath")
	private String imageSrc;

	////////////////////////////////////////////////////////////////////////////////
	/*
	 * Mapping fields
	 */
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * The gallery images are only loaded when you hit the individual Listing page,
	 * but not on the Browse page (which is where Listing data is best displayed
	 * without details). Please see Listing.imageSrc for the image URL for the
	 * Browse page
	 */
	@ManyToOne
	@JoinColumn(name = "LISTING_DETAIL_ID")
	@JsonIdentityReference(alwaysAsId = true)
	@Schema(description = "Product details of the product in the image")
	private ListingDetails listingDetail;

	public GalleryImage() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public ListingDetails getListingDetail() {
		return listingDetail;
	}

	public void setListingDetail(ListingDetails listingDetail) {
		this.listingDetail = listingDetail;
	}

}
