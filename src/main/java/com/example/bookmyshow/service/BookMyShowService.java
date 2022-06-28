package com.example.bookmyshow.service;

import com.example.bookmyshow.api.BookingRequest;
import com.example.bookmyshow.dto.ShowDTO;
import com.example.bookmyshow.dto.ShowSeatDTO;
import com.example.bookmyshow.exception.CustomerNotFoundException;
import com.example.bookmyshow.exception.InvalidBookingException;
import com.example.bookmyshow.exception.SeatUnavailableException;
import com.example.bookmyshow.model.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface BookMyShowService {

    public List<ShowDTO> findAllShowsByMovieAndDateAndCity(String moviename, LocalDate date, String city);

    public List<ShowSeatDTO> findAllAvailableSeatsForShow(Long showid);

    public String reserveSeats(BookingRequest bookingRequest);

    public String confirmSeats(BookingRequest bookingRequest);

}
