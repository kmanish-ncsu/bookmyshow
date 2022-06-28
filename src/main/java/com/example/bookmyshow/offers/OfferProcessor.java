package com.example.bookmyshow.offers;

import com.example.bookmyshow.model.Booking;
import com.example.bookmyshow.model.ShowSeat;

import java.util.List;

public interface OfferProcessor {

    public void process(List<ShowSeat> showSeats, Booking booking);

}
