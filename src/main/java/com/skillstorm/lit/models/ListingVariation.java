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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Tracks which listing is a variant of another. The intention is to have
 * listings be siblings of each other.. however, in a relational model, this is
 * a challenge that we decided to solve with a Parent-child listing design. We
 * likely need to refactor this to NoSQL (graph or document).
 * 
 * Recommended URL: /listings/v1/{UUID}/variants
 */
@Entity
@Table(name = "LISTING_VARIATIONS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListingVariation {

	@Id
	@GeneratedValue(generator = "LISTING_VARIATION_UUID")
	@GenericGenerator(name = "LISTING_VARIATION_UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "LISTING_VARIATION_ID")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "LISTING_ID")
	private Listing listing;

	/**
	 * Color, Style, Size, Package Size, etc.
	 */
	@Column(name = "VARIATION_TYPE")
	private String type;

	/**
	 * Text to display If type is color, seller may say: Blue, Red, Green, etc.
	 */
	@Column(name = "VARIATION_DESCRIPTION")
	private String description;

	public ListingVariation() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}