package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final TravelRequestRepository travelRequestRepository;

    public ManagerServiceImpl(TravelRequestRepository travelRequestRepository) {
        this.travelRequestRepository = travelRequestRepository;
    }

    public List<TravelRequest> getPendingTravelRequests(Long managerId) {
        return travelRequestRepository.findPendingTravelRequestsByManagerId(managerId);
    }

    public TravelRequest approvedTravelRequestByManager(Long id) {
        TravelRequest travelRequest = travelRequestRepository.findById(id).orElse(null);
        if (travelRequest != null) {
            travelRequest.setStatus(TravelRequestState.APPROVEDBYMANAGER);
            return travelRequestRepository.save(travelRequest);
        }
        return null;
    }

    public TravelRequest correctionInTravelRequest(Long id, TravelRequest travelRequest) {
        TravelRequest updateTravelRequest = travelRequestRepository.findById(id).orElse(null);
        if (updateTravelRequest != null) {
            updateTravelRequest.setCorrectionMessage(travelRequest.getCorrectionMessage());
            updateTravelRequest.setStatus(TravelRequestState.REVIEW);
            return travelRequestRepository.save(updateTravelRequest);
        } else {
            return null;
        }
    }
}

