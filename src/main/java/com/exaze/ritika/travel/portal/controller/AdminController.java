package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.AdminServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;
    private final TravelRequestServiceImpl travelRequestServiceImpl;

    public AdminController(AdminServiceImpl adminServiceImpl, TravelRequestServiceImpl travelRequestServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
        this.travelRequestServiceImpl = travelRequestServiceImpl;
    }

    @Operation(summary = "Update travel request ticket-cost by adminteam")
    @PutMapping("/{adminId}/travel-requests/{requestId}/update-ticket-cost")
    public ResponseEntity<TravelRequest> updateTicketCost(@PathVariable Long requestId, @RequestBody Map<String, Double> requestBody) {
        try {
            TravelRequest existingRequest = travelRequestServiceImpl.getTravelRequestById(requestId);
            if (existingRequest == null) {
                return ResponseEntity.notFound().build();
            }
            if (requestBody.containsKey("ticketCost")) {
                Double newTicketCost = requestBody.get("ticketCost");
                existingRequest.setTicketCost(newTicketCost);
                TravelRequest updatedRequest = adminServiceImpl.updateTravelRequest(existingRequest);
                return ResponseEntity.ok(updatedRequest);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update travel request ticket-cost by adminteam")
    @PutMapping("/{adminId}/travel-requests/{requestId}/update-cab-cost")
    public ResponseEntity<TravelRequest> updateCabCost(@PathVariable Long requestId, @RequestBody Map<String, Double> requestBody) {
        try {
            // Check if the requested travel request exists
            TravelRequest existingRequest = travelRequestServiceImpl.getTravelRequestById(requestId);
            if (existingRequest == null) {
                return ResponseEntity.notFound().build();
            }
            if (requestBody.containsKey("cabCost")) {
                Double newTicketCost = requestBody.get("cabCost");
                existingRequest.setCabCost(newTicketCost);
                TravelRequest updatedRequest = adminServiceImpl.updateTravelRequest(existingRequest);
                return ResponseEntity.ok(updatedRequest);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get approved travel request by manager")
    @GetMapping("/travel-requests/approved-by-manager")
    public ResponseEntity<List<TravelRequest>> getApprovedTravelRequestByManager() {
        try {
            List<TravelRequest> approvedTravelRequestByManager = adminServiceImpl.getApprovedByManagerTravelRequests();
            return ResponseEntity.ok(approvedTravelRequestByManager);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update travel request by admin request using requestId")
    @PutMapping("/travel-requests/{requestId}/approve-by-admin")
    public ResponseEntity<TravelRequest> approveTravelRequestByAdmin(@PathVariable Long requestId) {
        try {
            TravelRequest approvedRequest = travelRequestServiceImpl.approveTravelRequestByAdminTeam(requestId);
            return ResponseEntity.ok(approvedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
