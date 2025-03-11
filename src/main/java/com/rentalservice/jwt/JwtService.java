package com.rentalservice.jwt;

import com.rentalservice.model.AccessToken;
import com.rentalservice.model.entity.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator keyGenerator;

    public AccessToken generateToken(User user){
        //criando o token
        var key = keyGenerator.getKey();
        var expirationDate = generateExpirationDate();
        var claims = generateTokenClaims(user);

        String token = Jwts
                .builder()
                .signWith(key)
                .subject(user.getEmail())//id unico para identificar o usuario
                .expiration(expirationDate)
                .claims(claims) // informacoes do usuario no map
                .compact();

        return new AccessToken(token);
    }

    private Date generateExpirationDate(){
        var expirationMinutes = 60;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        System.out.println("Expiration time: " + now); // Verifique o horário calculado
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        return claims;
    }

    public String getEmailFromToken(String tokenJwt){
        try {
            JwtParser build = Jwts.parser()
                    .verifyWith(keyGenerator.getKey())
                    .build();

            Jws<Claims> jwsClaims = build.parseSignedClaims(tokenJwt);
            Claims claims = jwsClaims.getPayload();
            return claims.getSubject();

        }catch (JwtException e){
            throw new InvalidTokenException(e.getMessage());
        }
    }
}