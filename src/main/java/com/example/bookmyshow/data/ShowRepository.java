package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query(value = "SELECT s from Theater t, Movie m, Show s where m.name=?1 and s.date=?2 and t.city=?3")
    List<Show> findAllShows(String name, Date date, String city);

    @Query(value = "select s.* from show s,movie m, theater t where s.theater_id=t.id and s.movie_id=m.id and m.name=?1 and s.date=?2 and t.city=?3", nativeQuery = true)
    List<Show> findAllShowsNative(String name, LocalDate date, String city);

}