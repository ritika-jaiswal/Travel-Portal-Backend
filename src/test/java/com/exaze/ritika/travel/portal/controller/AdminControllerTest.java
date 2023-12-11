package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.AdminServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private TravelRequestServiceImpl travelRequestServiceImpl;

    @Mock
    private AdminServiceImpl adminServiceImpl;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testUpdateTicketCost() throws Exception {
        Long adminId = 1L;
        Long requestId = 1L;
        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("ticketCost", 5000.0);

        TravelRequest existingRequest = new TravelRequest();
        Mockito.when(travelRequestServiceImpl.getTravelRequestById(requestId)).thenReturn(existingRequest);
        Mockito.when(adminServiceImpl.updateTravelRequest(existingRequest)).thenReturn(existingRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/{adminId}/travel-requests/{requestId}/update-ticket-cost", adminId, requestId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ticketCost\":5000.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));

    }

    @Test
    void testUpdateCabCost() throws Exception {
        Long adminId = 1L;
        Long requestId = 1L;
        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("cabCost", 600.0);

        TravelRequest existingRequest = new TravelRequest();
        Mockito.when(travelRequestServiceImpl.getTravelRequestById(requestId)).thenReturn(existingRequest);
        Mockito.when(adminServiceImpl.updateTravelRequest(existingRequest)).thenReturn(existingRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/{adminId}/travel-requests/{requestId}/update-cab-cost", adminId, requestId)
                     .contentType(MediaType.APPLICATION_JSON)
                     .content("{\"cabCost\":600.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testGetApprovedTravelRequestByManager() throws Exception {
        List<TravelRequest> approvedRequests = Arrays.asList(new TravelRequest(), new TravelRequest());
        Mockito.when(adminServiceImpl.getApprovedByManagerTravelRequests()).thenReturn((approvedRequests));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/travel-requests/approved-by-manager"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    void testApproveTravelRequestByAdmin() throws Exception{
        Long requestId = 1L;
        TravelRequest approvedRequest = new TravelRequest();
        Mockito.when(travelRequestServiceImpl.approveTravelRequestByAdminTeam(requestId)).thenReturn(approvedRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/travel-requests/{id}/approve-by-admin", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }
}