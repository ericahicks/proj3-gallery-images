package com.skillstorm.lit.models;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "LISTING_DETAILS")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ListingDetails {

	@Id
	@GeneratedValue(generator = "LISTING_DETAILS_UUID")
	@GenericGenerator(name = "LISTING_DETAILS_UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "LISTING_DETAILS_ID")
	@Type(type = "uuid-char")
	private UUID id;

	@Lob
	@Basic(optional = true)
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PRODUCT_MATERIAL")
	private String material;

	@Column(name = "DIMENSIONS_DESCRIPTION")
	private String dimensionsDescription;

	@Column(name = "WEIGHT_DESCRIPTION")
	private String weightDescription;

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE")
	private Date releaseDate;

	@Lob
	@Basic(optional = true)
	@Column(name = "SAFETY_INFORMATION")
	private String safetyInformation;

	@Lob
	@Basic(optional = true)
	@Column(name = "WARRANTY_INFORMATION")
	private String warrantyInformation;

	////////////////////////////////////////////////////////////////////////////////
	/*
	 * Mapping fields
	 */
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Max of 4
	 */
	@OneToMany(mappedBy = "listingDetail", fetch = FetchType.LAZY)
//	@JsonManagedReference
	private Set<GalleryImage> galleryImages;

	////////////////////////////////////////////////////////////////////////////////
	/*
	 * Audit fields
	 */
	////////////////////////////////////////////////////////////////////////////////

	@CreatedBy
	@JsonIgnore
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreatedDate
	@JsonIgnore
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@LastModifiedDate
	@JsonIgnore
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	@LastModifiedBy
	@JsonIgnore
	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	public ListingDetails() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDimensionsDescription() {
		return dimensionsDescription;
	}

	public void setDimensionsDescription(String dimensionsDescription) {
		this.dimensionsDescription = dimensionsDescription;
	}

	public String getWeightDescription() {
		return weightDescription;
	}

	public void setWeightDescription(String weightDescription) {
		this.weightDescription = weightDescription;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSafetyInformation() {
		return safetyInformation;
	}

	public void setSafetyInformation(String safetyInformation) {
		this.safetyInformation = safetyInformation;
	}

	public String getWarrantyInformation() {
		return warrantyInformation;
	}

	public void setWarrantyInformation(String warrantyInformation) {
		this.warrantyInformation = warrantyInformation;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Set<GalleryImage> getGalleryImages() {
		return galleryImages;
	}

	public void setGalleryImages(Set<GalleryImage> galleryImages) {
		this.galleryImages = galleryImages;
	}

	@Override
	public String toString() {
		return "ListingDetails [description=" + description + ", releaseDate=" + releaseDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListingDetails other = (ListingDetails) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// For Testing purposes only, compares all fields except the id field
	public boolean isSame(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListingDetails other = (ListingDetails) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dimensionsDescription == null) {
			if (other.dimensionsDescription != null)
				return false;
		} else if (!dimensionsDescription.equals(other.dimensionsDescription))
			return false;
		if (galleryImages == null) {
			if (other.galleryImages != null)
				return false;
		} else if (!galleryImages.equals(other.galleryImages))
			return false;
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null)
				return false;
		} else if (!lastModifiedBy.equals(other.lastModifiedBy))
			return false;
		if (lastModifiedDate == null) {
			if (other.lastModifiedDate != null)
				return false;
		} else if (!lastModifiedDate.equals(other.lastModifiedDate))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (safetyInformation == null) {
			if (other.safetyInformation != null)
				return false;
		} else if (!safetyInformation.equals(other.safetyInformation))
			return false;
		if (warrantyInformation == null) {
			if (other.warrantyInformation != null)
				return false;
		} else if (!warrantyInformation.equals(other.warrantyInformation))
			return false;
		if (weightDescription == null) {
			if (other.weightDescription != null)
				return false;
		} else if (!weightDescription.equals(other.weightDescription))
			return false;
		return true;
	}
	
	
}
