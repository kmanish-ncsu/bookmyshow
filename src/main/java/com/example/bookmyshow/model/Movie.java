package com.example.bookmyshow.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "movie_seq", initialValue = 1, allocationSize=1)
    private Long id;

    private String name;

    public Movie(Long movieid) {
        this.id = movieid;
    }
}