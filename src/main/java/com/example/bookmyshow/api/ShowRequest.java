package com.example.bookmyshow.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ShowRequest {
    String moviename;
    LocalDate date;
    String city;

}
