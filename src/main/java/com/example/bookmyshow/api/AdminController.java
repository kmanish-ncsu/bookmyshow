package com.example.bookmyshow.api;

import com.example.bookmyshow.model.*;
import com.example.bookmyshow.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @PostMapping(path = "/theaters", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTheater(@RequestBody List<Theater> theaters){
        adminService.addTheaters(theaters);
    }

    @PostMapping(path = "/theaterseats", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTheaterSeats(@RequestBody List<TheaterSeat> theaterSeats){
        adminService.addTheaterSeats(theaterSeats);
    }

    @PostMapping(path = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovies(@RequestBody List<Movie> movies){
        adminService.addMovies(movies);
    }

    @PostMapping(path = "/shows", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addShows(@RequestBody List<Show> shows){
        adminService.addShows(shows);
    }

    @PostMapping(path = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomers(@RequestBody List<Customer> theaterSeats){
        adminService.addCustomers(theaterSeats);
    }

    @PostMapping(path = "/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffers(@RequestBody List<Offer> offers){
        adminService.addOffers(offers);
    }
}
