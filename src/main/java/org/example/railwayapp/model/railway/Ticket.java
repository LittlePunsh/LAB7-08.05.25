package org.example.railwayapp.model.railway;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets", schema = "railway_station")
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "passenger_name", length = 100)
    private String passengerName;

    @Column(name = "seat_number", length = 10)
    private String seatNumber;

    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime purchaseDate;
}
