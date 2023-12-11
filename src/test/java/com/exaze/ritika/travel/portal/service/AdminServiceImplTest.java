package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private TravelRequestRepository travelRequestRepository;

    private AdminServiceImpl adminServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminServiceImpl = new AdminServiceImpl(travelRequestRepository);
    }

    @Test
    void testUpdateTravelRequest() {
        TravelRequest travelRequest = new TravelRequest();
        when(travelRequestRepository.save(travelRequest)).thenReturn(travelRequest);

        TravelRequest result = adminServiceImpl.updateTravelRequest(travelRequest);

        assertEquals(travelRequest, result);
        verify(travelRequestRepository, times(1)).save(travelRequest);
    }

    @Test
    void testGetApprovedByManagerTravelRequests() {
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());
        when(travelRequestRepository.findManagerApprovedTravelRequests()).thenReturn(expectedRequests);

        List<TravelRequest> result = adminServiceImpl.getApprovedByManagerTravelRequests();

        assertEquals(expectedRequests, result);
        verify(travelRequestRepository, times(1)).findManagerApprovedTravelRequests();
    }
}
