package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelTeamServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/travel-team")
public class TravelTeamController {

    private final TravelTeamServiceImpl travelTeamServiceImpl;
    private final TravelRequestServiceImpl travelRequestServiceImpl;

    public TravelTeamController(TravelTeamServiceImpl travelTeamServiceImpl, TravelRequestServiceImpl travelRequestServiceImpl) {
        this.travelTeamServiceImpl = travelTeamServiceImpl;
        this.travelRequestServiceImpl = travelRequestServiceImpl;
    }

    @Operation(summary = "Confirmed travel request by travel team")
    @PutMapping("/travel-requests/{requestId}/ticket-confirmed-by-travelteam")
    public ResponseEntity<TravelRequest> confirmTravelRequestByTravelTeam(@PathVariable Long requestId) {
        try {
            TravelRequest approvedRequest = travelTeamServiceImpl.confirmTravelRequestByTravelTeam(requestId);
            return ResponseEntity.ok(approvedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all pending travel request for a specific managerID")
    @GetMapping("/travel-requests/approved-by-adminteam")
    public ResponseEntity<List<TravelRequest>> getApprovedTravelRequestByAdmin() {
        try {
            List<TravelRequest> approvedTravelRequestByAdmin = travelTeamServiceImpl.getApprovedTravelRequestByAdmin();
            return ResponseEntity.ok(approvedTravelRequestByAdmin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete travel request using a specific requestId ")
    @DeleteMapping("travel-requests/{requestId}/declined-request")
    public ResponseEntity<String> deleteTravelRequest(@PathVariable Long requestId) {
        try {
            TravelRequest existingRequest = travelTeamServiceImpl.getTravelRequestById(requestId);
            if (existingRequest == null) {
                return ResponseEntity.notFound().build();
            }
            travelTeamServiceImpl.deleteTravelRequest(requestId);
            return ResponseEntity.ok("Travel request deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete travel request");
        }
    }

    @Operation(summary = "Declined travel request by travel team using requestId")
    @PutMapping("/travel-requests/{requestId}/decline")
    public ResponseEntity<String> declineTravelRequestByTravelTeam(@PathVariable Long requestId) {
        try {
            TravelRequest existingRequest = travelRequestServiceImpl.getTravelRequestById(requestId);
            if (existingRequest == null) {
                return ResponseEntity.notFound().build();
            }
            boolean updated = travelTeamServiceImpl.updateTravelRequestStatus(requestId, "Declined");
            if (updated) {
                return ResponseEntity.ok("Travel request status changed to Declined");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update travel request status");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to decline travel request");
        }
    }

    @Operation(summary = "Upload a travel ticket by travel team")
    @PutMapping("/travel-requests/{requestId}/upload-file")
    public ResponseEntity<TravelRequest> uploadTicketByTravelTeam(
            @PathVariable Long requestId,
            @RequestParam("file") MultipartFile file) {
        try {
            TravelRequest travelRequest = travelRequestServiceImpl.getTravelRequestById(requestId);
            if (travelRequest == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            TravelRequest updatedTravelRequest = travelTeamServiceImpl.saveFile(requestId, file, travelRequest);

            if (updatedTravelRequest != null) {
                return ResponseEntity.ok(updatedTravelRequest);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
