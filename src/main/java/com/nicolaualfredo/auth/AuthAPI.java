package com.nicolaualfredo.auth;

import com.nicolaualfredo.auth.handler.CorsHandler;
import com.nicolaualfredo.auth.handler.LoginHandler;
import com.nicolaualfredo.auth.handler.RegisterHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author nicolaualfredo
 */
public class AuthAPI {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/register", new CorsHandler(new RegisterHandler()));
        server.createContext("/login", new CorsHandler(new LoginHandler()));

        server.start();
        System.out.println("🔐Authentication API running on http://localhost:8000");
    }
}
