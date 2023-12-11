package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;

import java.util.List;

public interface ManagerService {

    List<TravelRequest> getPendingTravelRequests(Long managerId);

    TravelRequest approvedTravelRequestByManager(Long id);

    TravelRequest correctionInTravelRequest(Long id, TravelRequest travelRequest);
}

