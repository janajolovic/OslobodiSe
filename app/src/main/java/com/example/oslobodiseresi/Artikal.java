package com.example.oslobodiseresi;

public class Artikal {
    private int id;
    private String naziv;
    private String urlSlike;

    private String opis;

    public Artikal(int id, String naziv, String urlSlike, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.urlSlike = urlSlike;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getUrlSlike() {
        return urlSlike;
    }

    public int getId() { return id; }

    public String getOpis() {
        return opis;
    }
}

