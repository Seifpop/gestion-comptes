package com.formation.jee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Responsible for generating, parsing, and validating JSON Web Tokens (JWT).
 * <p>
 * It provides methods for extracting the username from a JWT token, generating a new JWT token,
 * and building a JWT token with custom claims.
 */
@ApplicationScoped
public class JwtProvider {
    /**
     * The issuer of the JWT tokens.
     */
    public static final String AUTH_ISSUER = "fil-rouge";

    /**
     * The expiration time of the JWT tokens in milliseconds (5 hours).
     */
    public static final long EXPIRATION_TOKEN = 5 * 60 * 60 * 1000; // 5 hours

    /**
     * The secret key used to sign and verify the JWT tokens.
     */
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the JWT token.
     * @throws JwtException If the token is invalid or cannot be parsed.
     */
    public String extractUsername(String token) throws JwtException {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver A function that resolves the claim from the JWT token's payload.
     * @param <T>            The type of the claim to be extracted.
     * @return The extracted claim value.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted claims from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates a new JWT token with the provided subject (typically the username).
     *
     * @param subject The subject (username) to be included in the JWT token.
     * @return The generated JWT token.
     */
    public String generateToken(String subject) {
        return buildToken(new HashMap<>(), subject);
    }

    /**
     * Builds a new JWT token with the provided extra claims and subject.
     *
     * @param extraClaims Additional claims to be included in the JWT token.
     * @param subject     The subject (username) to be included in the JWT token.
     * @return The generated JWT token.
     */
    public String buildToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuer(AUTH_ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }
}
