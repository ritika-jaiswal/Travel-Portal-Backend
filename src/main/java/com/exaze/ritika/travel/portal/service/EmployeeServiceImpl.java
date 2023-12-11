package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.User;
import com.exaze.ritika.travel.portal.repository.TravelRequestRepository;
import com.exaze.ritika.travel.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TokenService tokenService;

    @Autowired
    TravelRequestRepository travelRequestRepository;

    @Autowired
    private TravelRequestServiceImpl travelRequestServiceImpl;

    public EmployeeServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public String authenticateEmployee(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String authToken = tokenService.generateToken(user);
            return authToken;
        } else {
            return null;
        }
    }
    public TravelRequest createTravelRequest(TravelRequest travelRequest) {
        User existingUser = userRepository.findByUsername(travelRequest.getUser().getUsername());

        if (existingUser == null) {
            throw new TravelRequestServiceImpl.UserNotFoundException("User not found with username: " + travelRequest.getUser().getUsername());
        }
        travelRequest.setUser(existingUser);
        return travelRequestRepository.save(travelRequest);
    }

    public TravelRequest updateTravelRequest(Long requestId , TravelRequest travelRequest) {
        return travelRequestRepository.save(travelRequest);
    }
}

