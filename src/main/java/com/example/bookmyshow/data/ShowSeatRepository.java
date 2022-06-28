package com.example.bookmyshow.data;

import com.example.bookmyshow.model.Show;
import com.example.bookmyshow.model.ShowSeat;
import com.example.bookmyshow.model.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Query(value = "SELECT ss.* FROM SHOW_SEAT ss where ss.show_id=?1 and ss.status <> 'CONFIRMED'", nativeQuery = true)
    List<ShowSeat> findAllShowSeatsNative(Long showid);

    List<ShowSeat> findAllByShowIsAndStatusIsNotOrStatusIsNot(Show show, String status1, String status2);

    @Query(value = "SELECT ss.* FROM SHOW_SEAT ss where ss.show_id=?1 and ss.status NOT IN (?2,?3)", nativeQuery = true)
    List<ShowSeat> findAllNonPendingNonConfirmedShowSeatsNative(Long showid, String status1, String status2);

    @Query(value = "SELECT ss.* FROM SHOW_SEAT ss where ss.show_id=?1 and ss.status IN (?2)", nativeQuery = true)
    List<ShowSeat> findAllPendingShowSeatsNative(Long showid, String status);

    List<ShowSeat> findAllByStatusIs(String status);

    List<ShowSeat> findAllByBookingId(Long bookingId);
}