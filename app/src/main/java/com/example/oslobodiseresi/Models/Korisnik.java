package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Korisnik {
    @SerializedName("id")
    private String Id;
    @SerializedName("userName")
    private String ime;
    private String email;
    private String brojTelefona;
    private int brojOcena;
    private float zbirOcena;
    private String slika;
    private ArrayList<Item> omiljeniOglasi;
    private ArrayList<Integer> lajkovaniKomentari;

    public Korisnik(String Id, String ime, String email, String brojTelefona, int brojOcena, int zbirOcena) {
        this.Id = Id;
        this.ime = ime;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.brojOcena = brojOcena;
        this.zbirOcena = zbirOcena;
    }

    public String getSlika() {
        return slika;
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

    public int getBrojOcena() {
        return brojOcena;
    }

    public float getZbirOcena() {
        return zbirOcena;
    }

    public ArrayList<Item> getOmiljeniOglasi() {
        return omiljeniOglasi;
    }

    public ArrayList<Integer> getLajkovaniKomentari() { return lajkovaniKomentari; }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "Id='" + Id + '\'' +
                ", ime='" + ime + '\'' +
                ", email='" + email + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                ", brojOcena=" + brojOcena +
                ", zbirOcena=" + zbirOcena +
                '}';
    }
}
