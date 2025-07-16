/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.auth.model;

import java.time.LocalDateTime;

/**
 *
 * @author nicolaualfredo
 */
public class User {

    private String username;
    private String email;
    private String hashedPassword;
    private LocalDateTime createdAt;

    public User() {
        // Default constructor for JSON
    }

    public User(String username, String email, String hashedPassword, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
