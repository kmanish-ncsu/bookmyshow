package com.example.bookmyshow.dto;

import com.example.bookmyshow.model.Movie;
import com.example.bookmyshow.model.Show;
import com.example.bookmyshow.model.Theater;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ShowDTO {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private Movie movie;
    private Theater theater;

    public ShowDTO(Show show) {
        this.id=show.getId();
        this.date=show.getDate();
        this.startTime=show.getStartTime();
        this.movie=show.getMovie();
    }
}
