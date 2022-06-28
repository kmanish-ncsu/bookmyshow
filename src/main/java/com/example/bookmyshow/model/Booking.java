package com.example.bookmyshow.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="booking_seq")
    @SequenceGenerator(name = "booking_seq", sequenceName = "booking_seq", initialValue = 1, allocationSize=1)
    private Long id;

    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer bookedBy;
}