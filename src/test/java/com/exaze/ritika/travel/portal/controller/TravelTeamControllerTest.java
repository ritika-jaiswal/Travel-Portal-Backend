package com.exaze.ritika.travel.portal.controller;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.service.TravelRequestServiceImpl;
import com.exaze.ritika.travel.portal.service.TravelTeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class TravelTeamControllerTest {

    @Mock
    private TravelTeamServiceImpl travelTeamServiceImpl;

    @Mock
    private TravelRequestServiceImpl travelRequestServiceImpl;

    @InjectMocks
    private TravelTeamController travelTeamController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(travelTeamController).build();
    }

    @Test
    void testConfirmTravelRequestByTravelTeam() throws Exception{
        Long requestId = 1L;
        TravelRequest approvedRequest = new TravelRequest();
        Mockito.when(travelTeamServiceImpl.confirmTravelRequestByTravelTeam(requestId)).thenReturn(approvedRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/travel-team/travel-requests/{requestId}/ticket-confirmed-by-travelteam", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void testGetApprovedTravelRequestByAdmin() throws Exception {
        List<TravelRequest> approvedRequests = Arrays.asList(new TravelRequest(), new TravelRequest());
        Mockito.when(travelTeamServiceImpl.getApprovedTravelRequestByAdmin()).thenReturn(approvedRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/travel-team/travel-requests/approved-by-adminteam"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    void testDeleteTravelRequest() throws Exception {
        Long requestId = 1L;
        TravelRequest existingRequest = new TravelRequest();

        Mockito.when(travelTeamServiceImpl.getTravelRequestById(requestId)).thenReturn(existingRequest);
        Mockito.doNothing().when(travelTeamServiceImpl).deleteTravelRequest(requestId);

        mockMvc = MockMvcBuilders.standaloneSetup(travelTeamController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/travel-team/travel-requests/{requestId}/declined-request", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Travel request deleted successfully!"));
    }

    @Test
    void testDeclineTravelRequestByTravelTeam() throws Exception {
        Long requestId = 1L;
        TravelRequest existingRequest = new TravelRequest();
        Mockito.when(travelRequestServiceImpl.getTravelRequestById(requestId)).thenReturn(existingRequest);
        Mockito.when(travelTeamServiceImpl.updateTravelRequestStatus(requestId, "Declined")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/travel-team/travel-requests/{requestId}/decline", requestId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Travel request status changed to Declined"));
    }

    @Test
    void testUploadTicketByTravelTeam() throws Exception {
        Long requestId = 1L;
        TravelRequest travelRequest = new TravelRequest();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Demo Content!".getBytes());

        Mockito.when(travelRequestServiceImpl.getTravelRequestById(requestId)).thenReturn(travelRequest);
        Mockito.when(travelTeamServiceImpl.saveFile(eq(requestId), any(MultipartFile.class), eq(travelRequest)))
                .thenReturn(travelRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/travel-team/travel-requests/{requestId}/upload-file", requestId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content("Demo Content".getBytes()))  // Include the file content in the request body
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }
}