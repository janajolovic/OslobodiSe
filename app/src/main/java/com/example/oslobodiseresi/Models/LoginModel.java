package com.example.oslobodiseresi.Models;

public class LoginModel {
    public String email;
    public String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
