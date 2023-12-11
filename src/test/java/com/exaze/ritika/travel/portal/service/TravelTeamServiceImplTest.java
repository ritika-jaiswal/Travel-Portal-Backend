package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TravelTeamServiceImplTest {

    @Mock
    private TravelRequestRepository travelRequestRepository;

    @InjectMocks
    private TravelTeamServiceImpl travelTeamServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetApprovedTravelRequestByAdmin() {
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());

        when(travelRequestRepository.findAdminApprovedTravelRequests()).thenReturn(expectedRequests);

        List<TravelRequest> result = travelTeamServiceImpl.getApprovedTravelRequestByAdmin();

        assertEquals(expectedRequests, result);

        verify(travelRequestRepository, times(1)).findAdminApprovedTravelRequests();
    }

    @Test
    void testDeleteTravelRequest() {
        Long requestId = 1L;
        TravelRequest deleteTravelRequest = new TravelRequest();
        deleteTravelRequest.setId(requestId);

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(deleteTravelRequest));

        travelTeamServiceImpl.deleteTravelRequest(requestId);

        verify(travelRequestRepository, times(1)).findById(requestId);
        verify(travelRequestRepository, times(1)).deleteById(requestId);

        assertEquals(TravelRequestState.DECLINED, deleteTravelRequest.getStatus());
    }

    @Test
    void testUpdateTravelRequestStatus() {
        Long requestId = 1L;
        String newStatus = "DECLINED";
        TravelRequest updateTravelRequest = new TravelRequest();

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(updateTravelRequest));
        when(travelRequestRepository.save(updateTravelRequest)).thenReturn(updateTravelRequest);

        boolean result = travelTeamServiceImpl.updateTravelRequestStatus(requestId, newStatus);

        assertTrue(result);
        assertEquals(TravelRequestState.DECLINED, updateTravelRequest.getStatus());

        verify(travelRequestRepository, times(1)).findById(requestId);
        verify(travelRequestRepository, times(1)).save(updateTravelRequest);
    }
}
