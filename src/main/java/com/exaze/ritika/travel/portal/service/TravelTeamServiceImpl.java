package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.CabDetails;
import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TravelTeamServiceImpl implements TravelTeamService {

    private final TravelRequestRepository travelRequestRepository;
    private final TravelRequest travelRequest;

    public TravelTeamServiceImpl(TravelRequestRepository travelRequestRepository, TravelRequest travelRequest) {
        this.travelRequestRepository = travelRequestRepository;
        this.travelRequest = travelRequest;
    }
    public List<TravelRequest> getApprovedTravelRequestByAdmin() {
        // Implement logic to retrieve pending travel requests for the manager
        return travelRequestRepository.findAdminApprovedTravelRequests();
    }
    public TravelRequest getTravelRequestById(Long requestId) {
        return travelRequestRepository.findById(requestId).orElse(null);
    }

    public void deleteTravelRequest(Long requestId) {
        // Implement logic to delete the travel request
        TravelRequest deleteTravelRequest = travelRequestRepository.findById(requestId).orElse(null);
        if (deleteTravelRequest != null) {
            travelRequestRepository.deleteById(requestId);
            deleteTravelRequest.setStatus(TravelRequestState.DECLINED);
        }

    }

    public boolean updateTravelRequestStatus(Long requestId, String newStatus) {
        Optional<TravelRequest> optionalTravelRequest = travelRequestRepository.findById(requestId);

        if (optionalTravelRequest.isPresent()) {
            TravelRequest updateTravelRequest = optionalTravelRequest.get();
            updateTravelRequest.setStatus(TravelRequestState.DECLINED);
            travelRequestRepository.save(updateTravelRequest);
            return true;
        } else {
            return false;
        }
    }


    public TravelRequest saveFile(Long requestId, MultipartFile file, TravelRequest travelRequest) {
        try {
            String fileName = "file_" + requestId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File targetFile = new File(fileName);
            file.transferTo(targetFile);
            travelRequest.setConfirmTicket(targetFile);

            if (travelRequest != null) {
                travelRequest.setStatus(TravelRequestState.COMPLETED);
                travelRequestRepository.save(travelRequest);
            }
            return travelRequestRepository.save(travelRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TravelRequest confirmTravelRequestByTravelTeam(Long id) {
        TravelRequest confirmtravelRequest = travelRequestRepository.findById(id).orElse(null);
        if (confirmtravelRequest != null) {
            confirmtravelRequest.setStatus(TravelRequestState.COMPLETED);
            return travelRequestRepository.save(confirmtravelRequest);
        }
        return null;
    }
//    public void confirmTicket(Long travelRequestId) {
//
//        System.out.println("Travel Reqeuest Id " + travelRequestId);
//        TravelRequest travelRequest = travelRequestRepository.findTravelRequestById(travelRequestId).orElse(null);
//        System.out.println("After calling findTravelRequestById: " + travelRequest);
//        if (travelRequest != null) {
//            travelRequest.setTicketConfirmed(true);
//            travelRequestRepository.save(travelRequest);
//        }
//    }
//
//    public void confirmCabBooking(Long travelRequestId) {
//
//        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestId).orElse(null);
//        if (travelRequest != null) {
//            travelRequest.setCabBookingConfirmed(true);
//            travelRequestRepository.save(travelRequest);
//        }
//    }
//
//    public void uploadTicket(Long travelRequestId, TicketDetails ticketDetails) {
//        // Logic to upload booked ticket details for the given travel request
//        // Save ticket details in the database or perform necessary actions
//        // Example: travelRequest.setTicketDetails(ticketDetails);
//    }

    public void uploadCabDetails(Long travelRequestId, CabDetails cabDetails) {
        // Logic to upload booked cab details for the given travel request
        // Save cab details in the database or perform necessary actions
        // Example: travelRequest.setCabDetails(cabDetails);
    }
}

