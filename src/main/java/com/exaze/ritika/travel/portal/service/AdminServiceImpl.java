package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final TravelRequestRepository travelRequestRepository;

    public AdminServiceImpl(TravelRequestRepository travelRequestRepository) {
        this.travelRequestRepository = travelRequestRepository;
    }

    @Override
    public TravelRequest updateTravelRequest(TravelRequest travelRequest) {
        return travelRequestRepository.save(travelRequest);
    }

    @Override
    public List<TravelRequest> getApprovedByManagerTravelRequests() {
        return travelRequestRepository.findManagerApprovedTravelRequests();
    }
}

