package com.example.oslobodiseresi;

import android.graphics.Bitmap;

public class Artikal {
    private int id;
    private String naziv;
    private Bitmap slika;

    private String opis;

    public Artikal(int id, String naziv, Bitmap slika, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.slika = slika;
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public Bitmap getSlika() {
        return slika;
    }

    public int getId() { return id; }

    public String getOpis() {
        return opis;
    }
}

