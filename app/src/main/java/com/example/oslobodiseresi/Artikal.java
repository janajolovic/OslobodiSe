package com.example.oslobodiseresi;

public class Artikal {
    private String naziv;
    private String urlSlike;

    public Artikal(String naziv, String urlSlike) {
        this.naziv = naziv;
        this.urlSlike = urlSlike;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getUrlSlike() {
        return urlSlike;
    }
}

