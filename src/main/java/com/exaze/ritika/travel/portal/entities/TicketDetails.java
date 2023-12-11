package com.exaze.ritika.travel.portal.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ticket_details")
public class TicketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "travel_request_id")
    private TravelRequest travelRequest;

    @Column(name = "confirmed")
    private Boolean confirmed = false;

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = (confirmed != null) && confirmed;
    }
}
