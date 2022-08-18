package com.skillstorm.lit.models;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "LISTING")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {

	@Id
	@GeneratedValue(generator = "LISTING_UUID")
	@GenericGenerator(name = "LISTING_UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "LISTING_ID")
	private UUID id;

	@Column(name = "LISTING_TITLE")
	private String title;

	/**
	 * The Object URI to an image in Amazon S3 bucket. Do NOT include the S3 Bucket
	 * URL, this will change depending on the deployment environment.
	 */
	@Column(name = "LISTING_IMAGE_SRC")
	private String imageSrc;

	@Column(name = "LISTING_PRICE")
	private Double price;

	@Column(name = "LISTING_LANGUAGE_CODE")
	private String listingLanguage;

	@Column(name = "PRODUCT_BRAND")
	private String brand;

	@Column(name = "MANUFACTURER_RECOMMENDED_AGE")
	private String ageRange;

	////////////////////////////////////////////////////////////////////////////////
	/*
	 * Mapping fields
	 */
	////////////////////////////////////////////////////////////////////////////////

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "LISTING_DETAILS_ID")
	private ListingDetails details;
//
//	@ManyToOne
//	@JoinColumn(name = "USERNAME")
//	private Seller seller;

//	@ManyToMany
//	@JoinTable(name = "LISTING_CATEGORIES", joinColumns = @JoinColumn(name = "LISTING_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
//	private Set<Category> categories;

//	@ManyToOne
//	@JoinColumn(name = "LISTED_IN_COUNTRY")
//	private Country country;

	@OneToMany(mappedBy = "listing")
	@JsonIgnore
	private Set<ListingVariation> variants;

	////////////////////////////////////////////////////////////////////////////////
	/*
	 * Audit fields
	 */
	////////////////////////////////////////////////////////////////////////////////

//	@CreatedBy
//	@JsonIgnore
//	@Column(name = "CREATED_BY")
//	private String createdBy;

//	@CreatedDate
//	@JsonIgnore
//	@Column(name = "CREATED_DATE")
//	private Date createdDate;

//	@LastModifiedDate
//	@JsonIgnore
//	@Column(name = "LAST_MODIFIED_DATE")
//	private Date lastModifiedDate;

//	@LastModifiedBy
//	@JsonIgnore
//	@Column(name = "LAST_MODIFIED_BY")
//	private String lastModifiedBy;

	public Listing() {
		super();
	}

	public Listing(Listing listing) {
		this.id = listing.getId();
		this.title = listing.getTitle();
		this.imageSrc = listing.getImageSrc();
		this.price = listing.getPrice();
		this.listingLanguage = listing.getListingLanguage();
		this.brand = listing.getBrand();
		this.ageRange = listing.getAgeRange();
		this.details = listing.getDetails();
//		this.seller = listing.getSeller();
//		this.categories = listing.getCategories();
//		this.country = listing.getCountry();
//		this.createdBy = listing.getCreatedBy();
//		this.createdDate = listing.getCreatedDate();
//		this.lastModifiedDate = listing.getLastModifiedDate();
//		this.lastModifiedBy = listing.getLastModifiedBy();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getListingLanguage() {
		return listingLanguage;
	}

	public void setListingLanguage(String listingLanguage) {
		this.listingLanguage = listingLanguage;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public ListingDetails getDetails() {
		return details;
	}

	public void setDetails(ListingDetails details) {
		this.details = details;
	}
//
//	public Seller getSeller() {
//		return seller;
//	}
//
//	public void setSeller(Seller seller) {
//		this.seller = seller;
//	}
//
//	public Set<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}
//
//	public Country getCountry() {
//		return country;
//	}
//
//	public void setCountry(Country country) {
//		this.country = country;
//	}
//
//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public Date getLastModifiedDate() {
//		return lastModifiedDate;
//	}
//
//	public void setLastModifiedDate(Date lastModifiedDate) {
//		this.lastModifiedDate = lastModifiedDate;
//	}
//
//	public String getLastModifiedBy() {
//		return lastModifiedBy;
//	}
//
//	public void setLastModifiedBy(String lastModifiedBy) {
//		this.lastModifiedBy = lastModifiedBy;
//	}
//
//	public Set<ListingVariation> getVariants() {
//		return variants;
//	}
//
//	public void setVariants(Set<ListingVariation> variants) {
//		this.variants = variants;
//	}

	@Override
	public String toString() {
		return "Listing [id=" + id + ", title=" + title + ", imageSrc=" + imageSrc + ", price=" + price
				+ ", listingLanguage=" + listingLanguage + ", brand=" + brand + "]";
	}

}
