package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.TravelRequestState;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import com.exaze.ritika.travel.portal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TravelRequestServiceImplTest {

    @Mock
    private TravelRequestRepository travelRequestRepository;

    @Mock
    private UserRepository userRepository;

    private TravelRequestServiceImpl travelRequestServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        travelRequestServiceImpl = new TravelRequestServiceImpl(travelRequestRepository, userRepository);
    }

    @Test
    void testGetAllTravelRequestsForEmployee() {
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());

        when(travelRequestRepository.findAll()).thenReturn(expectedRequests);

        List<TravelRequest> result = travelRequestServiceImpl.getAllTravelRequestsForEmployee();

        assertEquals(expectedRequests, result);

        verify(travelRequestRepository, times(1)).findAll();
    }

    @Test
    void testGetTravelRequestById() {
        Long requestId = 1L;
        TravelRequest expectedRequest = new TravelRequest();

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(expectedRequest));

        TravelRequest result = travelRequestServiceImpl.getTravelRequestById(requestId);

        assertNotNull(result);
        assertEquals(expectedRequest, result);

        verify(travelRequestRepository, times(1)).findById(requestId);
    }

    @Test
    void testGetTravelRequestsByUserId() {
        Long userId = 1L;
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());

        when(travelRequestRepository.findTravelRequestsNotDraft(userId)).thenReturn(expectedRequests);

        List<TravelRequest> result = travelRequestServiceImpl.getTravelRequestsByUserId(userId);

        assertEquals(expectedRequests, result);

        verify(travelRequestRepository, times(1)).findTravelRequestsNotDraft(userId);
    }

    @Test
    void testGetDraftTravelRequestsByUserId() {
        Long userId = 1L;
        List<TravelRequest> expectedRequests = Collections.singletonList(new TravelRequest());

        when(travelRequestRepository.findByUserIdAndStatus(userId)).thenReturn(expectedRequests);

        List<TravelRequest> result = travelRequestServiceImpl.getDraftTravelRequestsByUserId(userId);

        assertEquals(expectedRequests, result);

        verify(travelRequestRepository, times(1)).findByUserIdAndStatus(userId);
    }

    @Test
    void testApproveTravelRequestByAdminTeam() {
        Long requestId = 1L;
        TravelRequest travelRequest = new TravelRequest();

        when(travelRequestRepository.findById(requestId)).thenReturn(Optional.of(travelRequest));
        when(travelRequestRepository.save(travelRequest)).thenReturn(travelRequest);

        TravelRequest result = travelRequestServiceImpl.approveTravelRequestByAdminTeam(requestId);

        assertNotNull(result);
        assertEquals(TravelRequestState.APPROVEDBYADMINTEAM, result.getStatus());

        verify(travelRequestRepository, times(1)).findById(requestId);
        verify(travelRequestRepository, times(1)).save(travelRequest);
    }

}
