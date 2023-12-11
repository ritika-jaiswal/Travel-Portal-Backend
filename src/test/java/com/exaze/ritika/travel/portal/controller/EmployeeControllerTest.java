package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.LoginRequest;
import com.exaze.ritika.travel.portal.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeServiceImpl employeeServiceImpl;
    @Mock
    private TravelRequestServiceImpl travelRequestServiceImpl;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testEmployeeLoginInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("invalidUsername", "invalidPassword");

        Mockito.when(employeeServiceImpl.authenticateEmployee(loginRequest.getUsername(), loginRequest.getPassword()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"invalidUsername\",\"password\":\"invalidPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }

    @Test
    void testEmployeeLoginValidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("validUsername", "validPassword");
        String authToken = "mockedAuthToken";

        Mockito.when(employeeServiceImpl.authenticateEmployee(loginRequest.getUsername(), loginRequest.getPassword()))
                .thenReturn(authToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"validUsername\",\"password\":\"validPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(authToken));
    }

    @Test
    void testCreateTravelRequest() throws Exception {
        TravelRequest createdRequest = new TravelRequest();
//Using any() to match any argument
        Mockito.when(employeeServiceImpl.createTravelRequest(any(TravelRequest.class)))
                .thenReturn(createdRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/travel-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testUpdateTravelRequest() throws Exception {
        Long requestId = 1L;
        TravelRequest updatedTravelRequest = new TravelRequest();

        // Use Mockito.eq for matching the specific arguments in value
        Mockito.when(employeeServiceImpl.updateTravelRequest(eq(requestId), any(TravelRequest.class)))
                .thenReturn(updatedTravelRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/travel-requests/{requestId}/update", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testViewAllTravelRequests() throws Exception {
        List<TravelRequest> travelRequests = Collections.singletonList(new TravelRequest());
        Mockito.when(travelRequestServiceImpl.getAllTravelRequestsForEmployee()).thenReturn(travelRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/travel-requests")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}]"));
    }

    @Test
    void testGetTravelRequestsByRequestId() throws Exception {
        Long requestId = 1L;
        TravelRequest travelRequest = new TravelRequest();
        Mockito.when(travelRequestServiceImpl.getTravelRequestById(requestId)).thenReturn(travelRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/travel-request/{requestId}", requestId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testGetTravelRequestsByUserId() throws Exception {
        Long userId = 1L;
        List<TravelRequest> travelRequests = Collections.singletonList(new TravelRequest());
        Mockito.when(travelRequestServiceImpl.getTravelRequestsByUserId(userId)).thenReturn(travelRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/travel-requests/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}]"));
    }

    @Test
    void testGetDraftTravelRequestsByUserId() throws Exception {
        Long userId = 1L;
        List<TravelRequest> draftTravelRequests = Collections.singletonList(new TravelRequest());
        Mockito.when(travelRequestServiceImpl.getDraftTravelRequestsByUserId(userId)).thenReturn(draftTravelRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/travel-requests/{userId}/draft", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}]"));
    }
}
