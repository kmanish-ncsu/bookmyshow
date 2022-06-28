package com.example.bookmyshow.offers;

import com.example.bookmyshow.data.BookingRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@Getter
@Setter
public class OffersConfig {

    @Autowired
    BookingRepository bookingRepository;

    @Value("${theater.seat.basic.price}")
    private BigDecimal basicPrice;

    @Value("${theater.seat.premium.price}")
    private BigDecimal premiumPrice;

    @Bean
    public OfferProcessorManager offerProcessorManager(){
        return new OfferProcessorManager(offerProcessors());
    }

    @Bean
    public List<OfferProcessor> offerProcessors(){
        List<OfferProcessor> offerProcessors = new ArrayList<>();
        offerProcessors.add(new AfternoonTicket20PercentOffer(bookingRepository, basicPrice, premiumPrice));
        offerProcessors.add(new ThirdTicket50PercentOffer(bookingRepository, basicPrice, premiumPrice));
        return Collections.unmodifiableList(offerProcessors);
    }

}
