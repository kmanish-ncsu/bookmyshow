package com.example.bookmyshow.offers;

import com.example.bookmyshow.data.BookingRepository;
import com.example.bookmyshow.data.ShowSeatRepository;
import com.example.bookmyshow.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class AfternoonTicket20PercentOffer implements OfferProcessor {

    public static final double TWENTY_PERCENT_OFF = 0.8;

    BookingRepository bookingRepository;

    private BigDecimal basicPrice;

    private BigDecimal premiumPrice;

    public AfternoonTicket20PercentOffer(BookingRepository bookingRepository, BigDecimal basicPrice, BigDecimal premiumPrice) {
        this.bookingRepository=bookingRepository;
        this.basicPrice=basicPrice;
        this.premiumPrice=premiumPrice;
    }

    @Override
    public void process(List<ShowSeat> showSeats, Booking booking) {
        if(CollectionUtils.isEmpty(showSeats)) {return;}

        Theater theater = showSeats.get(0).getTheaterSeat().getTheater();
        List<Offer> offers = theater.getOffers();
        if(!CollectionUtils.isEmpty(offers) && offers.stream().filter(offer -> offer.getOfferType().equals(Offer.OfferType.AFTERNOON_TICKET)).findFirst().isPresent()){
            Show show = showSeats.get(0).getShow();
            if(afternoonShow(show)){
                booking.setTotalAmount(booking.getTotalAmount().multiply(BigDecimal.valueOf(TWENTY_PERCENT_OFF)));
                bookingRepository.save(booking);
            }
        }
    }

    private boolean afternoonShow(Show show) {
        // Afternoon is 12pm to 5pm
        LocalTime startTime = show.getStartTime();
        if(startTime.isAfter(LocalTime.parse("11:59")) && startTime.isBefore(LocalTime.parse("17:00"))){
            return true;
        }
        return false;
    }

}
