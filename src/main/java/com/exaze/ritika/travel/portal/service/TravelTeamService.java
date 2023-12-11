package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.CabDetails;
import com.exaze.ritika.travel.portal.entities.TravelRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TravelTeamService {

    List<TravelRequest> getApprovedTravelRequestByAdmin();

    TravelRequest getTravelRequestById(Long requestId);

    void deleteTravelRequest(Long requestId);

    boolean updateTravelRequestStatus(Long requestId, String newStatus);

    TravelRequest saveFile(Long requestId, MultipartFile file, TravelRequest travelRequest);

    TravelRequest confirmTravelRequestByTravelTeam(Long id);

    void uploadCabDetails(Long travelRequestId, CabDetails cabDetails);
}
