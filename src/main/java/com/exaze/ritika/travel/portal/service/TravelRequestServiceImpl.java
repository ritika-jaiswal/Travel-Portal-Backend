package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import com.exaze.ritika.travel.portal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelRequestServiceImpl implements TravelRequestService {

    private final TravelRequestRepository travelRequestRepository;
    private final UserRepository userRepository;
    @Autowired
    public TravelRequestServiceImpl(TravelRequestRepository travelRequestRepository, UserRepository userRepository) {
        this.travelRequestRepository = travelRequestRepository;
        this.userRepository = userRepository;
    }

    public List<TravelRequest> getAllTravelRequestsForEmployee() {
        return travelRequestRepository.findAll();
    }

    public TravelRequest getTravelRequestById(Long requestId) {
        return travelRequestRepository.findById(requestId).orElse(null);
    }

    public List<TravelRequest> getTravelRequestsByUserId(Long userId) {
        return travelRequestRepository.findTravelRequestsNotDraft(userId);
    }

    public List<TravelRequest> getDraftTravelRequestsByUserId(Long userId) {
        return travelRequestRepository.findByUserIdAndStatus(userId);
    }
    public List<TravelRequest> getReviewTravelRequestsByUserId(Long userId) {
        return travelRequestRepository.findByUserIdAndReviewStatus(userId);
    }

    @Transactional
    public TravelRequest approveTravelRequestByAdminTeam(Long requestId) {
        TravelRequest travelRequest = travelRequestRepository.findById(requestId).orElse(null);
        if (travelRequest != null) {
            travelRequest.setStatus(TravelRequestState.APPROVEDBYADMINTEAM);
            return travelRequestRepository.save(travelRequest);
        }
        return null;
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
