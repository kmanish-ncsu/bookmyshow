package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}