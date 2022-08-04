package com.example.mailService.security;

import com.example.mailService.domain.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${authentication.secret-key}")
    private String secretToken;

    @Value("${authentication.expiration-time}")
    private long tokenExpirationMilliSec;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenExpirationMilliSec);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretToken)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretToken)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretToken)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException | IllegalArgumentException exception) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            log.error("Unsupported JWT token");
        }
        return false;
    }

}
