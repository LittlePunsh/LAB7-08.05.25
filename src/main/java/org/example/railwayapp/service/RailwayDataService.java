package org.example.railwayapp.service;

import org.example.railwayapp.dto.AdminTripDto;
import org.example.railwayapp.model.railway.Ticket;
import org.example.railwayapp.model.railway.Trip;
import org.example.railwayapp.repository.railway.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RailwayDataService {

    @Autowired
    private TripRepository tripRepository;

    @Transactional(value = "railwayTransactionManager", readOnly = true)
    public List<Trip> getAllTripsOrdered() {
        return tripRepository.findAllByOrderByDepartureTimeAsc();
    }

    @Transactional(value = "railwayTransactionManager", readOnly = true)
    public List<AdminTripDto> getAllTripsWithTicketsForAdmin() {
        List<Trip> tripsWithTickets = tripRepository.findAllWithTicketsOrderByDepartureTimeAsc();
        return tripsWithTickets.stream()
                .flatMap(trip -> {
                    if (trip.getTickets() == null || trip.getTickets().isEmpty()) {
                        return List.of(new AdminTripDto(trip, null)).stream();
                    }
                    return trip.getTickets().stream()
                            .map(ticket -> new AdminTripDto(trip, ticket));
                })
                .collect(Collectors.toList());
    }
}
