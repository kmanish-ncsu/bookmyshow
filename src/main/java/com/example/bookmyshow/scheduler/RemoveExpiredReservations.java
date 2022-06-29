package com.example.bookmyshow.scheduler;

import com.example.bookmyshow.data.BookingRepository;
import com.example.bookmyshow.data.ShowSeatRepository;
import com.example.bookmyshow.model.Booking;
import com.example.bookmyshow.model.ShowSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RemoveExpiredReservations {

    public static final int DELETE_UNRESERVED_POLL_FREQUENCY = 10000;

    @Value("${payment.session.timeout.in.msec}")
    private Integer sessionTimeout;

    @Value("${polling.frequency.in.msec : 10000}")
    private static final int pollingFrequency = 10000;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    BookingRepository bookingRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    //Need transactions because we are changing 2 tables: updating SHOW_SEAT & deleting from BOOKING table
    @Scheduled(initialDelay = pollingFrequency, fixedDelay = pollingFrequency)
    public void removeExpiredReservations(){
        //fetch all PENDING ShowSeats
        List<ShowSeat> pendingShowSeats = showSeatRepository.findAllByStatusIs(ShowSeat.BookingStatus.RESERVED_PAYMENT_PENDING);
        if(!CollectionUtils.isEmpty(pendingShowSeats)){
            //if now() - createddate > SESION_TIMEOUT then status="" and delete Booking
            for(ShowSeat showSeat: pendingShowSeats)
            if(Duration.between(showSeat.getReservationTime(), LocalDateTime.now()).toMillis() > sessionTimeout){
                Booking booking = showSeat.getBooking();
                showSeat.setStatus(ShowSeat.BookingStatus.UNRESERVED);
                showSeat.setBooking(null);
                showSeat.setReservationTime(null);
                showSeatRepository.save(showSeat);
                em.flush();
                bookingRepository.delete(booking);
            }
        }
    }
}
