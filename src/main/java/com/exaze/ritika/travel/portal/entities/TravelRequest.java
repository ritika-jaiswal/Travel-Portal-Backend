package com.exaze.ritika.travel.portal.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "travel_requests")
public class TravelRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "travel_team_id")
    private TravelTeam travelTeam;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TravelRequestState status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "from_city")
    private String from;

    @Column(name = "to_city")
    private String to;

    @Column(name = "cab_request")
    private String cabRequest;

    @Column(name = "pickup_location")
    private String pickUpLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "ticket_cost")
    private double ticketCost;

    @Column(name = "cab_cost")
    private double cabCost;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "ticket")
    private File confirmTicket;

    @Column(name = "correction_msg")
    private String correctionMessage;

    public TravelRequest(User existingUser) {
        this.user = existingUser;
    }

    @JsonCreator

    public TravelRequest(
            @JsonProperty("user") User user,
            @JsonProperty("employee") Employee employee,
            @JsonProperty("manager") Manager manager,
            @JsonProperty("admin") Admin admin,
            @JsonProperty("travelTeam") TravelTeam travelTeam,
            @JsonProperty("status") TravelRequestState status,
            @JsonProperty("requestType") String requestType,
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("cabRequest") String cabRequest,
            @JsonProperty("pickUpLocation") String pickUpLocation,
            @JsonProperty("dropLocation") String dropLocation,
            @JsonProperty("ticketCost") double ticketCost,
            @JsonProperty("cabCost") double cabCost,
            @JsonProperty("departureDate") Date departureDate,
            @JsonProperty("returnDate") Date returnDate,
            @JsonProperty("confirmTicket") File confirmTicket) {
        this.user = user;
        this.employee = employee;
        this.manager = manager;
        this.admin = admin;
        this.travelTeam = travelTeam;
        this.status = status;
        this.creationDate = LocalDateTime.now();
        this.requestType = requestType;
        this.from = from;
        this.to = to;
        this.cabRequest = cabRequest;
        this.pickUpLocation = pickUpLocation;
        this.dropLocation = dropLocation;
        this.ticketCost = ticketCost;
        this.cabCost = cabCost;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.confirmTicket = confirmTicket;
    }
}
