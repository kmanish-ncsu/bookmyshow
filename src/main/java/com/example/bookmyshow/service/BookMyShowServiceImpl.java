package com.example.bookmyshow.service;

import com.example.bookmyshow.api.BookingRequest;
import com.example.bookmyshow.data.*;
import com.example.bookmyshow.dto.ShowDTO;
import com.example.bookmyshow.dto.ShowSeatDTO;
import com.example.bookmyshow.exception.CustomerNotFoundException;
import com.example.bookmyshow.exception.InvalidBookingException;
import com.example.bookmyshow.exception.SeatUnavailableException;
import com.example.bookmyshow.model.*;
import com.example.bookmyshow.offers.OfferProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMyShowServiceImpl implements BookMyShowService {

    public static final String BOOKING_CONFIRMED = "Booking Confirmed.";
    public static final String SEATS_UNAVAILABLE = "Seats Unavailable.";
    public static final String RESERVATION_SUCCESSFUL = "Reservation Successful.";
    public static final String INVALID_BOOKING = "Invalid Booking.";
    public static final int SEAT_COST = 100;
    public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";

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

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    @Qualifier("afternoon-ticket-offer")
    OfferProcessor offerProcessor;

    @Value("${theater.seat.basic.price}")
    private BigDecimal basicPrice;

    @Value("${theater.seat.basic.price}")
    private BigDecimal premiumPrice;

    @Transactional
     public List<ShowDTO> findAllShowsByMovieAndDateAndCity(String moviename, LocalDate date, String city){
        List<Show> shows = showRepository.findAllShowsNative(moviename, date, city);
        return shows.stream().map((show -> new ShowDTO(show))).collect(Collectors.toList());
    }

     public List<ShowSeatDTO> findAllAvailableSeatsForShow(Long showid){
         List<ShowSeat> showSeats = showSeatRepository.findAllNonPendingNonConfirmedShowSeatsNative(showid, ShowSeat.BookingStatus.CONFIRMED.toString(), ShowSeat.BookingStatus.RESERVED_PAYMENT_PENDING.toString());
         List<ShowSeatDTO> collect = showSeats.stream().map(showSeat -> new ShowSeatDTO(showSeat)).collect(Collectors.toList());
         System.out.println("ShowSeatDTO size "+collect.size());
         return collect;
     }

     @Transactional
     public String reserveSeats(BookingRequest bookingRequest){
         List<ShowSeat> showSeats = showSeatRepository.findAllById(bookingRequest.getSeats());
         //This locks all showseats to be booked for customer
         try{
             validateReservation(showSeats);
             //all seats available for reservation
             Customer customer = customerRepository.findById(bookingRequest.getCustomerId()).orElseThrow(CustomerNotFoundException::new);
             Booking booking = new Booking();
             booking.setBookedBy(customer);
             booking.setTotalAmount(getPaymentAmount(showSeats, bookingRequest));
             offerProcessor.process(showSeats, booking);
             booking = bookingRepository.save(booking);
             for(ShowSeat showSeat: showSeats){
                 showSeat.setBooking(booking);
                 showSeat.setStatus(ShowSeat.BookingStatus.RESERVED_PAYMENT_PENDING);
                 showSeat.setReservationTime(LocalDateTime.now());
             }
             return RESERVATION_SUCCESSFUL;
         }catch (SeatUnavailableException e) {
             return SEATS_UNAVAILABLE;
         } catch (CustomerNotFoundException e) {
             return CUSTOMER_NOT_FOUND;
         }
     }

    private BigDecimal getPaymentAmount(List<ShowSeat> showSeats, BookingRequest bookingRequest) {
         BigDecimal total = new BigDecimal(0);
         for(ShowSeat showSeat: showSeats){
             TheaterSeat.SeatType seatType = showSeat.getTheaterSeat().getSeatType();
             switch (seatType){
                 case BASIC:
                    total = total.add(basicPrice);
                     break;
                 case PREMIUM:
                     total = total.add(premiumPrice);
                     break;
             }
         }
        return total;
    }

    private void validateReservation(List<ShowSeat> showSeats) throws SeatUnavailableException {
        if(CollectionUtils.isEmpty(showSeats) || showSeats.stream().anyMatch(showSeat -> showSeat.getStatus()!=ShowSeat.BookingStatus.UNRESERVED)){
            throw new SeatUnavailableException();
        }
    }

    @Transactional
     //called once payment is done or session times out
     public String confirmSeats(BookingRequest bookingRequest){
         List<ShowSeat> showSeats = showSeatRepository.findAllById(bookingRequest.getSeats());
        try {
            validateSeats(showSeats);
            validateBooking(showSeats, bookingRequest);
            for(ShowSeat showSeat: showSeats){
                showSeat.setStatus(ShowSeat.BookingStatus.CONFIRMED);
            }
            return BOOKING_CONFIRMED;
        } catch (SeatUnavailableException e) {
            return SEATS_UNAVAILABLE;
        } catch (InvalidBookingException e) {
            return INVALID_BOOKING;
        }
    }

    private void validateBooking(List<ShowSeat> showSeats, BookingRequest bookingRequest) throws InvalidBookingException {
        for(ShowSeat showSeat: showSeats){
            if(showSeat.getBooking().getBookedBy().getId() != bookingRequest.getCustomerId()){
                throw new InvalidBookingException();
            }
        }
    }

    private void validateSeats(List<ShowSeat> showSeats) throws SeatUnavailableException {
        for(ShowSeat showSeat: showSeats){
            if(ShowSeat.BookingStatus.RESERVED_PAYMENT_PENDING!=showSeat.getStatus()){
                throw new SeatUnavailableException();
            }
        }
    }

}
