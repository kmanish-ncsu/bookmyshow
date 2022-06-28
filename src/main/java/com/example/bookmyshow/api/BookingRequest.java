package com.example.bookmyshow.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingRequest {
    Long showId;
    List<Long> seats;
    Long customerId;

}
