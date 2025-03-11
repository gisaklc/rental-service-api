package com.rentalservice.jwt;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import io.jsonwebtoken.*;

@Component
public class SecretKeyGenerator {

    private SecretKey key;

    public SecretKey getKey() {
        if (key == null) {
            key = Jwts.SIG.HS256.key().build();//chave de assinatura aleatoria
        }
        return key;
    }
}