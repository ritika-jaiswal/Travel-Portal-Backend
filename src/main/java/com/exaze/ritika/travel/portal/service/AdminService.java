package com.exaze.ritika.travel.portal.service;



import com.exaze.ritika.travel.portal.entities.TravelRequest;

import java.util.List;

public interface AdminService {

    TravelRequest updateTravelRequest(TravelRequest travelRequest);

    List<TravelRequest> getApprovedByManagerTravelRequests();
}

