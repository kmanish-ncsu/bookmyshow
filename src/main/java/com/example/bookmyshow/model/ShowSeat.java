package com.example.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "show_seat")
@Getter
@Setter
public class ShowSeat {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="showseat_seq")
    @SequenceGenerator(name = "showseat_seq", sequenceName = "showseat_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    private LocalDateTime reservationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_seat_id")
    @JsonIgnore
    private TheaterSeat theaterSeat;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.UNRESERVED;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public enum BookingStatus {
        UNRESERVED, RESERVED_PAYMENT_PENDING, CONFIRMED
    }
}