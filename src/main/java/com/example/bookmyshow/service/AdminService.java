package com.example.bookmyshow.service;

import com.example.bookmyshow.model.*;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminService {
    void addTheaters(List<Theater> theaters);

    void addMovies(List<Movie> movies);

    void addShows(List<Show> shows);

    void addTheaterSeats(List<TheaterSeat> theaterSeats);

    void addCustomers(List<Customer> customers);

    void addOffers(List<Offer> offers);
}
