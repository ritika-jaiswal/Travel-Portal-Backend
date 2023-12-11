package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.LoginRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.EmployeeServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Travel Portal Api's", description = "Include all modules APIs in Travel Portal")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private final TravelRequestServiceImpl travelRequestServiceImpl;

    public EmployeeController(EmployeeServiceImpl employeeServiceImpl, TravelRequestServiceImpl travelRequestServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
        this.travelRequestServiceImpl = travelRequestServiceImpl;
    }

    @Operation(summary = "It Match's the login credentials and logged in user in application id user is valid")
    @PostMapping("/login")
    public ResponseEntity<String> employeeLogin(@RequestBody LoginRequest loginRequest) {
        try {
            String authToken = employeeServiceImpl.authenticateEmployee(loginRequest.getUsername(), loginRequest.getPassword());
            if (authToken != null) {
                return ResponseEntity.ok(authToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @Operation(summary = "It creates a travel request")
    @PostMapping("/travel-requests")
    public ResponseEntity<TravelRequest> createTravelRequest(@RequestBody TravelRequest travelRequest) {
        try {
            TravelRequest createdRequest = employeeServiceImpl.createTravelRequest(travelRequest);
            return ResponseEntity.ok(createdRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update a travel request by requestId")
    @PutMapping("/travel-requests/{requestId}/update")
    public ResponseEntity<TravelRequest> updateTravelRequest(@PathVariable Long requestId, @RequestBody TravelRequest updatedTravelRequest) {
        try {
            TravelRequest updatedRequest = employeeServiceImpl.updateTravelRequest(requestId, updatedTravelRequest);
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all travel request")
    @GetMapping("/travel-requests")
    public ResponseEntity<List<TravelRequest>> viewAllTravelRequests() {
        try {
            List<TravelRequest> travelRequests = travelRequestServiceImpl.getAllTravelRequestsForEmployee();
            return ResponseEntity.ok(travelRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all travel request by requestId")
    @GetMapping("/travel-request/{requestId}")
    public ResponseEntity<TravelRequest> getTravelRequestsByRequestId(@PathVariable Long requestId) {
        try {
            TravelRequest travelRequests = travelRequestServiceImpl.getTravelRequestById(requestId);
            return ResponseEntity.ok(travelRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all travel request by userId")
    @GetMapping("/travel-requests/{userId}")
    public ResponseEntity<List<TravelRequest>> getTravelRequestsByUserId(@PathVariable Long userId) {
        try {
            List<TravelRequest> travelRequests = travelRequestServiceImpl.getTravelRequestsByUserId(userId);
            return ResponseEntity.ok(travelRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all draft travel request by userID")
    @GetMapping("/travel-requests/{userId}/draft")
    public ResponseEntity<List<TravelRequest>> getDraftTravelRequestsByUserId(@PathVariable Long userId) {
        try {
            List<TravelRequest> draftTravelRequests = travelRequestServiceImpl.getDraftTravelRequestsByUserId(userId);
            return ResponseEntity.ok(draftTravelRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all review travel request by userID")
    @GetMapping("/travel-requests/{userId}/review")
    public ResponseEntity<List<TravelRequest>> getReviewTravelRequestsByUserId(@PathVariable Long userId) {
        try {
            List<TravelRequest> reviewTravelRequests = travelRequestServiceImpl.getReviewTravelRequestsByUserId(userId);
            return ResponseEntity.ok(reviewTravelRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
