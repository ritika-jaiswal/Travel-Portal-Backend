package com.exaze.ritika.travel.portal.service;

import com.exaze.ritika.travel.portal.entities.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;


@Service
public class TokenServiceImpl implements TokenService {
    private static final String SECRET_KEY = generateSecureKey();

    private static final long EXPIRATION_TIME = 86400000;

    private static String generateSecureKey() {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    @Override
    public String generateToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return token;
    }
}
