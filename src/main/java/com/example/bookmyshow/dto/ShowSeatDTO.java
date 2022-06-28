package com.example.bookmyshow.dto;

import com.example.bookmyshow.model.Booking;
import com.example.bookmyshow.model.Show;
import com.example.bookmyshow.model.ShowSeat;
import com.example.bookmyshow.model.TheaterSeat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ShowSeatDTO {
    private Long id;
    private Long showid;
    private LocalDateTime reservationTime;
    private ShowSeat.BookingStatus status;
    private Booking booking;

    public ShowSeatDTO(ShowSeat showSeat) {
        this.id=showSeat.getId();
        this.showid=showSeat.getShow().getId();
        this.reservationTime=showSeat.getReservationTime();
        this.status=showSeat.getStatus();
        this.booking=showSeat.getBooking();
    }
}
