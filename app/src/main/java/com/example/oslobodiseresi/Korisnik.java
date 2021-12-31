package com.example.oslobodiseresi;

import com.google.gson.annotations.SerializedName;

public class Korisnik {
    private String Id;
    @SerializedName("userName")
    private String ime;
    private String email;
    private String brojTelefona;
    private int brojOcena;
    private int zbirOcena;

    public Korisnik(String Id, String ime, String email, String brojTelefona, int brojOcena, int zbirOcena) {
        this.Id = Id;
        this.ime = ime;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.brojOcena = brojOcena;
        this.zbirOcena = zbirOcena;
    }

    public String getIme() {
        return ime;
    }

    public String getEmail() {
        return email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public int getBrojOcena() {
        return brojOcena;
    }

    public int getZbirOcena() {
        return zbirOcena;
    }
}
