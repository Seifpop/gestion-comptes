package com.formation.jee.service;

import com.formation.jee.models.User;
import com.formation.jee.security.JwtProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;

import static com.formation.jee.filters.AuthFilter.AUTHORIZATION_NAME;

/**
 * Provides functionality for user authentication, sign-up, and token generation.
 */
@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager em;

    @Inject
    private JwtProvider jwtProvider;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(); // used for password hashing.

    /**
     * Authenticates a user by verifying the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return {@code true} if the authentication is successful, {@code false} otherwise.
     */
    public boolean authenticate(String username, String password) {
        // Find the user by the provided username
        User user = em.find(User.class, username);

        // If the user is not found, return false
        if (user == null) {
            return false;
        }

        // Use the password encoder to match the provided password with the stored hashed password
        return PASSWORD_ENCODER.matches(password, user.getPassword());
    }

    /**
     * Registers a new user in the database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @throws IllegalArgumentException if the user already exists.
     */
    public void signUp(String username, String password) {
        // Check if the user already exists
        User user = findByUsername(username);
        if (user != null) {
            throw new IllegalArgumentException("User already exists.");
        }

        // prepare the model
        user = new User();
        user.setUsername(username);
        user.setPassword(PASSWORD_ENCODER.encode(password)); // Encode the password

        // begin transaction
        em.getTransaction().begin();

        // save entity
        em.persist(user);

        // commit transaction
        em.flush();
        em.getTransaction().commit();
    }

    /**
     * Finds a user by the provided username.
     *
     * @param username The username of the user to be found.
     * @return The {@link User} entity, or {@code null} if the user is not found.
     */
    public User findByUsername(String username) {
        return em.find(User.class, username);
    }

    /**
     * Prepares an authentication cookie for the user.
     *
     * @param username The username of the user.
     * @return A {@link Cookie} containing the JWT token.
     */
    public Cookie prepareAuthCookie(String username) {
        // Generate a JWT token for the user
        String token = jwtProvider.generateToken(username);

        // Create a new Cookie with the token
        Cookie tokenCookie = new Cookie(AUTHORIZATION_NAME, token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(5 * 60 * 60); // 5 hours
        tokenCookie.setComment("To verify the identity of the current user.");

        return tokenCookie;
    }
}
