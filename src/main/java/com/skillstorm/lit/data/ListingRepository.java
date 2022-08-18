package com.skillstorm.lit.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.lit.models.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID>{


	@Query("from Listing l left join l.details d where l.id like CONCAT('%',:id,'%')")
    public Listing findListingById(@Param(value = "id") UUID id);

}
