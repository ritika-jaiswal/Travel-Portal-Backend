package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;

import java.util.List;

public interface TravelRequestService {

    List<TravelRequest> getAllTravelRequestsForEmployee();

    TravelRequest getTravelRequestById(Long requestId);

    List<TravelRequest> getTravelRequestsByUserId(Long userId);

    List<TravelRequest> getDraftTravelRequestsByUserId(Long userId);

    List<TravelRequest> getReviewTravelRequestsByUserId(Long userId);

    TravelRequest approveTravelRequestByAdminTeam(Long requestId);

    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
