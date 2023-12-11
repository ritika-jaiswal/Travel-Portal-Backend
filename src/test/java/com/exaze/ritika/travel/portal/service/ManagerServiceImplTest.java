package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @Mock
    private TravelRequestRepository travelRequestRepository;

    private ManagerServiceImpl managerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        managerServiceImpl = new ManagerServiceImpl(travelRequestRepository);
    }

    @Test
    void testGetPendingTravelRequests() {
        Long managerId = 1L;
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());
        when(travelRequestRepository.findPendingTravelRequestsByManagerId(managerId)).thenReturn(expectedRequests);
        List<TravelRequest> result = managerServiceImpl.getPendingTravelRequests(managerId);
        assertEquals(expectedRequests, result);
        verify(travelRequestRepository, times(1)).findPendingTravelRequestsByManagerId(managerId);
    }

    @Test
    void testApprovedTravelRequestByManager() {
        Long requestId = 1L;
        TravelRequest travelRequest = new TravelRequest();

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(travelRequest));
        when(travelRequestRepository.save(travelRequest)).thenReturn(travelRequest);

        TravelRequest result = managerServiceImpl.approvedTravelRequestByManager(requestId);

        assertNotNull(result);
        assertEquals(TravelRequestState.APPROVEDBYMANAGER, result.getStatus());

        verify(travelRequestRepository, times(1)).findById(requestId);
        verify(travelRequestRepository, times(1)).save(travelRequest);
    }

    @Test
    void testCorrectionInTravelRequest() {
        Long requestId = 1L;
        TravelRequest existingTravelRequest = new TravelRequest();
        TravelRequest updatedTravelRequest = new TravelRequest();
        updatedTravelRequest.setCorrectionMessage("Updated Correction Message");

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(existingTravelRequest));
        when(travelRequestRepository.save(existingTravelRequest)).thenReturn(existingTravelRequest);

        TravelRequest result = managerServiceImpl.correctionInTravelRequest(requestId, updatedTravelRequest);

        assertNotNull(result);
        assertEquals("Updated Correction Message", result.getCorrectionMessage());

        verify(travelRequestRepository, times(1)).findById(requestId);
        verify(travelRequestRepository, times(1)).save(existingTravelRequest);
    }
}
