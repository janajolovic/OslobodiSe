package com.example.oslobodiseresi;

public class Korisnik {
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private String brojTelefona;

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public Korisnik(String ime, String prezime, String email, String lozinka, String brojTelefona) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.brojTelefona = brojTelefona;
    }
}
