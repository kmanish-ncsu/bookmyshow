package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}