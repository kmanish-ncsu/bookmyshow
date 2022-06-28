package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Show;
import com.example.bookmyshow.model.Theater;
import com.example.bookmyshow.model.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterSeatRepository extends JpaRepository<TheaterSeat, Long> {

    List<TheaterSeat> findByTheater(Theater theater);
}