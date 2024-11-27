package com.alfredo.compraventaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        String password = "1234"; // La contraseña sin cifrar
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println(encodedPassword);
    }

}
