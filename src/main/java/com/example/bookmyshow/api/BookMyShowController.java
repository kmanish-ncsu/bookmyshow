package com.example.bookmyshow.api;

import com.example.bookmyshow.dto.ShowDTO;
import com.example.bookmyshow.dto.ShowSeatDTO;
import com.example.bookmyshow.service.BookMyShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bms")
public class BookMyShowController {

    @Autowired
    BookMyShowServiceImpl bookMyShowService;

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

//    @PostMapping(path = "/theaters", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addTheater(@RequestBody List<Theater> theaters){
//        bookMyShowService.addTheaters(theaters);
//    }
//
//    @PostMapping(path = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addMovies(@RequestBody List<Movie> movies){
//        bookMyShowService.addMovies(movies);
//    }
//
//    @PostMapping(path = "/shows", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addShows(@RequestBody List<Show> shows){
//        bookMyShowService.addShows(shows);
//    }
//
//    @PostMapping(path = "/theaterseats", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addTheaterSeats(@RequestBody List<TheaterSeat> theaterSeats){
//        bookMyShowService.addTheaterSeats(theaterSeats);
//    }
//
//    @PostMapping(path = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addCustomers(@RequestBody List<Customer> theaterSeats){
//        bookMyShowService.addCustomers(theaterSeats);
//    }
}
