package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.ManagerServiceImpl;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ManagerControllerTest {

    @Mock
    private ManagerServiceImpl managerServiceImpl;

    @Mock
    private TravelRequestServiceImpl travelRequestServiceImpl;

    @InjectMocks
    private ManagerController managerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
    }

    @Test
    void testGetPendingTravelRequests() throws Exception {
        Long managerId = 1L;
        List<TravelRequest> pendingRequest = Arrays.asList(new TravelRequest(), new TravelRequest());

        Mockito.when(managerServiceImpl.getPendingTravelRequests(managerId)).thenReturn(pendingRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/managers/{managerId}/travel-requests/pending", managerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    void testApproveTravelRequestByManager() throws Exception{
        Long requestId = 1L;
        TravelRequest approvedRequest = new TravelRequest();

        Mockito.when(managerServiceImpl.approvedTravelRequestByManager(requestId)).thenReturn(approvedRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/managers/travel-requests/{id}/approve-by-manager", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testCorrectionInTravelRequest() throws Exception {
        Long requestId = 1L;
        TravelRequest correctedRequest = new TravelRequest();

        Mockito.when(managerServiceImpl.correctionInTravelRequest(eq(requestId), any(TravelRequest.class))).thenReturn(correctedRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/managers/travel-requests/{id}/correction", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }
}