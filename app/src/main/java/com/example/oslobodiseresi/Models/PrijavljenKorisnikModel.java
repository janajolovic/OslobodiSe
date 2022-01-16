package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrijavljenKorisnikModel {
    @SerializedName("id")
    private String Id;
    @SerializedName("userName")
    private String ime;
    private String email;
    private String brojTelefona;
    private int brojOcena;
    private int zbirOcena;
    private ArrayList<Item> omiljeniArtikli;

    public int getBrojOcena() {
        return brojOcena;
    }

    public int getZbirOcena() {
        return zbirOcena;
    }

    public String getId() {
        return Id;
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

    public ArrayList<Item> getOmiljeniArtikli() {
        return omiljeniArtikli;
    }

    public Korisnik getKorisnik(){
        return new Korisnik(Id, ime, email, brojTelefona, brojOcena, zbirOcena);
    }
}
