package com.example.bookmyshow.offers;

import com.example.bookmyshow.data.BookingRepository;
import com.example.bookmyshow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

public class ThirdTicket50PercentOffer implements OfferProcessor {

    private static final double FIFTY_PERCENT_OFF = 0.5;

    private BookingRepository bookingRepository;

    private BigDecimal basicPrice;

    private BigDecimal premiumPrice;

    public ThirdTicket50PercentOffer(BookingRepository bookingRepository, BigDecimal basicPrice, BigDecimal premiumPrice) {
        this.bookingRepository=bookingRepository;
        this.basicPrice=basicPrice;
        this.premiumPrice=premiumPrice;
    }

    @Override
    public void process(List<ShowSeat> showSeats, Booking booking) {
        if(CollectionUtils.isEmpty(showSeats)) {return;}

        Theater theater = showSeats.get(0).getTheaterSeat().getTheater();
        List<Offer> offers = theater.getOffers();
        if(!CollectionUtils.isEmpty(offers) && offers.stream().filter(offer -> offer.getOfferType().equals(Offer.OfferType.THIRD_TICKET)).findFirst().isPresent()){
            if(showSeats.size() > 0){
                BigDecimal total = new BigDecimal(0);
                for(int i=0;i<showSeats.size();i++){
                    TheaterSeat.SeatType seatType = showSeats.get(i).getTheaterSeat().getSeatType();
                    switch (seatType){
                        case BASIC:
                            total = total.add(i>0 ? basicPrice.multiply(BigDecimal.valueOf(FIFTY_PERCENT_OFF)): basicPrice);
                            break;
                        case PREMIUM:
                            total = total.add(i>0 ? premiumPrice.multiply(BigDecimal.valueOf(FIFTY_PERCENT_OFF)): premiumPrice);
                            break;
                    }
                }
                booking.setTotalAmount(total);
                bookingRepository.save(booking);
            }
        }
    }
}