/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.auth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nicolaualfredo.auth.model.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nicolaualfredo
 *
 * Service responsible for loading, saving and managing user data.
 */
public class AuthService {

    private static final String FILE_PATH = "data/users.json";
    private final ObjectMapper mapper;

    public AuthService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Enable Java 8 date/time support
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Write human-readable dates
    }

    /**
     * Loads all registered users from the JSON file.
     *
     * @return a list of users or an empty list if file does not exist or fails
     * to load
     */
    public List<User> loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves a new user to the JSON file.
     *
     * @param user the user to save
     */
    public void saveUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email
     * @return an Optional containing the user if found, empty otherwise
     */
    public Optional<User> findUserByEmail(String email) {
        return loadUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Checks whether a user already exists with the given email.
     *
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    public boolean emailExists(String email) {
        return findUserByEmail(email).isPresent();
    }

    /**
     * Validates user login credentials.
     *
     * @param email user's email
     * @param hashedPassword hashed password
     * @return true if credentials match, false otherwise
     */
    public boolean validateCredentials(String email, String hashedPassword) {
        return findUserByEmail(email)
                .map(user -> user.getHashedPassword().equals(hashedPassword))
                .orElse(false);
    }
}
