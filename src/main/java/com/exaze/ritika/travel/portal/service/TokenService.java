package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.User;

public interface TokenService {
    String generateToken(User user);
}

