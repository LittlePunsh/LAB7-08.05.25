package org.example.railwayapp.model.railway;

import com.fasterxml.jackson.annotation.JsonIgnore; // Импорт Jackson аннотации
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "trips", schema = "railway_station")
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer tripId;

    @Column(name = "train_number", length = 10)
    private String trainNumber;

    @Column(name = "departure_station", length = 50)
    private String departureStation;

    @Column(name = "arrival_station", length = 50)
    private String arrivalStation;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "arrival_time")
    private String arrivalTime;

    // Связь с билетами: один рейс может иметь много билетов
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // <-- Добавляем эту аннотацию
    private Set<Ticket> tickets = new HashSet<>();
}