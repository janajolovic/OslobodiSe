package com.example.oslobodiseresi;

public class RegistarModel {
    private String userName;
    private String email;
    private String password;
    private String brojTelefona;

    public RegistarModel(String userName, String email, String password, String brojTelefona) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.brojTelefona = brojTelefona;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }
}
