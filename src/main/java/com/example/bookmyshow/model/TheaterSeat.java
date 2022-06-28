package com.example.bookmyshow.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "theater_seat")
@Getter@Setter
public class TheaterSeat {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="theaterseat_seq")
    @SequenceGenerator(name = "theaterseat_seq", sequenceName = "theaterseat_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    public enum SeatType{
        BASIC, PREMIUM
    }

}