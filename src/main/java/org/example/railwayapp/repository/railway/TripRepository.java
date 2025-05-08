package org.example.railwayapp.repository.railway;

import org.example.railwayapp.model.railway.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findAllByOrderByDepartureTimeAsc();

    @Query("SELECT t FROM Trip t LEFT JOIN FETCH t.tickets ORDER BY t.departureTime ASC")
    List<Trip> findAllWithTicketsOrderByDepartureTimeAsc();
}