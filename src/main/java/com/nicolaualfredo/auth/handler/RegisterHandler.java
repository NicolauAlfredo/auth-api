/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.auth.model.User;
import com.nicolaualfredo.auth.service.AuthService;
import com.nicolaualfredo.auth.util.PasswordUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicolaualfredo
 */
public class RegisterHandler implements HttpHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = new AuthService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        InputStream body = exchange.getRequestBody();
        Map<String, String> data = mapper.readValue(body, Map.class);

        String username = data.get("name");
        String email = data.get("email");
        String password = data.get("password");

        if (authService.emailExists(email)) {
            respond(exchange, 409, "Email already registered.");
            return;
        }

        String hashed = PasswordUtil.hashPassword(password);
        User user = new User(username, email, hashed, LocalDateTime.now());
        authService.saveUser(user);

        respond(exchange, 201, "User registered successfully.");
    }

    private void respond(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, 0);
        OutputStream os = exchange.getResponseBody();

        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        os.write(mapper.writeValueAsBytes(response));
        os.close();
    }
}
