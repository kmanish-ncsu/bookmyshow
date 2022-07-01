package com.example.bookmyshow.service;

import com.example.bookmyshow.data.*;
import com.example.bookmyshow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    TheaterSeatRepository theaterSeatRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @PersistenceContext
    EntityManager em;

     @Override
     public void addTheaters(List<Theater> theaters){
         for(Theater theater: theaters){
             theaterRepository.save(theater);
         }
     }

     @Override
     public void addMovies(List<Movie> movies){
         for(Movie movie: movies){
             movieRepository.save(movie);
         }
     }

     @Override
     @Transactional
     //We need transactions because we are adding rows to 2 tables SHOW & SHOW_SEAT
     public void addShows(List<Show> shows){
         for(Show show: shows){
             Show savedShow = showRepository.save(show);
             List<TheaterSeat> theaterSeats = theaterSeatRepository.findByTheater(savedShow.getTheater());
             for(TheaterSeat theaterSeat: theaterSeats){
                 ShowSeat showSeat = new ShowSeat();
                 showSeat.setShow(savedShow);
                 showSeat.setTheaterSeat(theaterSeat);
                showSeatRepository.save(showSeat);
             }
         }
     }

     @Override
     public void addTheaterSeats(List<TheaterSeat> theaterSeats){
         for(TheaterSeat theaterSeat: theaterSeats){
             theaterSeatRepository.save(theaterSeat);
         }
     }

     @Override
     public void addCustomers(List<Customer> customers){
         for(Customer customer : customers){
             customerRepository.save(customer);
         }
     }

     @Override
     @Transactional
     //We need transactions because we are updating 3 tables: OFFER, THEATER & THEATER_OFFERS
     public void addOffers(List<Offer> offers){
        for(Offer offer: offers){
            Offer persistedOffer= em.merge(offer);
            List<Theater> theaters = persistedOffer.getTheaters();
            for(int i=0;i<theaters.size();i++){
                Theater theater = theaters.get(i);
                theater.getOffers().add(persistedOffer);
                em.merge(theater);
            }
        }
     }
}
