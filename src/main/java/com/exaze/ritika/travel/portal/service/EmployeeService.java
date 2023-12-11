package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;

public interface EmployeeService {

    String authenticateEmployee(String username, String password);

    TravelRequest createTravelRequest(TravelRequest travelRequest);

    TravelRequest updateTravelRequest(Long requestId, TravelRequest travelRequest);
}

