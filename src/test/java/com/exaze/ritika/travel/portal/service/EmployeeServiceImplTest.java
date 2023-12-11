package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.User;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import com.exaze.ritika.travel.portal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private TravelRequestRepository travelRequestRepository;

    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeServiceImpl = new EmployeeServiceImpl(userRepository, tokenService);
        employeeServiceImpl.travelRequestRepository = travelRequestRepository;  // Set the mocked repository
    }

    @Test
    void testAuthenticateEmployee() {
        String username = "testuser";
        String password = "testpassword";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn("mockAuthToken");

        String authToken = employeeServiceImpl.authenticateEmployee(username, password);

        assertEquals("mockAuthToken", authToken);

        verify(userRepository, times(1)).findByUsername(username);
        verify(tokenService, times(1)).generateToken(mockUser);
    }

    @Test
    void testCreateTravelRequest() {
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setUser(new User());
        travelRequest.getUser().setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(new User());
        when(travelRequestRepository.save(travelRequest)).thenReturn(travelRequest);

        TravelRequest result = employeeServiceImpl.createTravelRequest(travelRequest);

        assertEquals(travelRequest, result);

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(travelRequestRepository, times(1)).save(travelRequest);
    }

    @Test
    void testUpdateTravelRequest() {
        Long requestId = 1L;
        TravelRequest travelRequest = new TravelRequest();

        when(travelRequestRepository.save(travelRequest)).thenReturn(travelRequest);

        TravelRequest result = employeeServiceImpl.updateTravelRequest(requestId, travelRequest);

        assertEquals(travelRequest, result);

        verify(travelRequestRepository, times(1)).save(travelRequest);
    }
}
