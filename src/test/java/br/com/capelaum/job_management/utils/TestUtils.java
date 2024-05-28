package br.com.capelaum.job_management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {
    public static String objectToJSON(Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofHours(3));

        return JWT.create().withIssuer("javagas")
                .withSubject(companyId.toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }
}
