package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.ManagerServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerServiceImpl managerServiceImpl;
    private final TravelRequestServiceImpl travelRequestServiceImpl;

    public ManagerController(ManagerServiceImpl managerServiceImpl, TravelRequestServiceImpl travelRequestServiceImpl) {
        this.managerServiceImpl = managerServiceImpl;
        this.travelRequestServiceImpl = travelRequestServiceImpl;
    }

    @Operation(summary = "Get all pending travel request for a specific managerID")
    @GetMapping("/{managerId}/travel-requests/pending")
    public ResponseEntity<List<TravelRequest>> getPendingTravelRequests(@PathVariable Long managerId) {
        try {
            List<TravelRequest> pendingRequests = managerServiceImpl.getPendingTravelRequests(managerId);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update travel request using requestId by manager ")
    @PutMapping("/travel-requests/{requestId}/approve-by-manager")
    public ResponseEntity<TravelRequest> approveTravelRequestByManager(@PathVariable Long requestId) {
        try {
            TravelRequest submittedRequest = managerServiceImpl.approvedTravelRequestByManager(requestId);
            return ResponseEntity.ok(submittedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "update travel request using requestId and change the state with a correction message")
    @PutMapping("/travel-requests/{requestId}/correction")
    public ResponseEntity<TravelRequest> correctionInTravelRequest(@PathVariable Long requestId, @RequestBody TravelRequest travelRequest) {
        try {
            TravelRequest submittedRequest = managerServiceImpl.correctionInTravelRequest(requestId, travelRequest);
            return ResponseEntity.ok(submittedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
