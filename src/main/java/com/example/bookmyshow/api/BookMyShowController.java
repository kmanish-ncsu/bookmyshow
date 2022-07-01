package com.example.bookmyshow.api;

import com.example.bookmyshow.dto.ShowDTO;
import com.example.bookmyshow.dto.ShowSeatDTO;
import com.example.bookmyshow.service.BookMyShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bms")
public class BookMyShowController {

    @Autowired
    BookMyShowService bookMyShowService;

    @GetMapping(path = "/movieshows", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ShowDTO> shows(@RequestBody ShowRequest showRequest){
        return bookMyShowService.findAllShowsByMovieAndDateAndCity(showRequest.moviename, showRequest.date, showRequest.city);
    }

    @GetMapping(path = "/showseats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ShowSeatDTO> showSeats(@RequestBody ShowSeatRequest showSeatRequest){
        return bookMyShowService.findAllAvailableSeatsForShow(showSeatRequest.getShowid());
    }

    @PostMapping(path = "/reserveseats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String bookSeats(@RequestBody BookingRequest bookingRequest){
        return bookMyShowService.reserveSeats(bookingRequest);
    }

    @PostMapping(path = "/confirmseats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String conSeats(@RequestBody BookingRequest bookingRequest){
        return bookMyShowService.confirmSeats(bookingRequest);
    }

}
