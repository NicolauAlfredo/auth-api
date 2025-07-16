/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.auth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.auth.model.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nicolaualfredo
 */
public class AuthService {

    private static final String FILE_PATH = "data/users.json";
    private final ObjectMapper mapper = new ObjectMapper();

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

    public void saveUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findUserByEmail(String email) {
        return loadUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean emailExists(String email) {
        return findUserByEmail(email).isPresent();
    }

    public boolean validateCredentials(String email, String hashedPassword) {
        return findUserByEmail(email)
                .map(user -> user.getHashedPassword().equals(hashedPassword))
                .orElse(false);
    }
}
