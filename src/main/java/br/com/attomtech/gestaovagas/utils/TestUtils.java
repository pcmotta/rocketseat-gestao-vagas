package br.com.attomtech.gestaovagas.utils;

import br.com.attomtech.gestaovagas.security.Roles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TestUtils {

    public static String objectToJson(Object obj) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(String idCompany, String secret) {
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        return JWT.create().withIssuer("javagas")
            .withSubject(idCompany)
            .withExpiresAt(expiresIn)
            .withClaim("roles", List.of(Roles.COMPANY.toString()))
            .sign(Algorithm.HMAC256(secret));
    }
}
