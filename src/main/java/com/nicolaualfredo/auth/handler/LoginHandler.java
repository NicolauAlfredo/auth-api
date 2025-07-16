/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.auth.service.AuthService;
import com.nicolaualfredo.auth.util.PasswordUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicolaualfredo
 */
public class LoginHandler implements HttpHandler {

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

        String email = data.get("email");
        String password = data.get("password");

        String hashedPassword = PasswordUtil.hashPassword(password);

        if (authService.validateCredentials(email, hashedPassword)) {
            respond(exchange, 200, "Login successful.");
        } else {
            respond(exchange, 401, "Invalid email or password.");
        }
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
