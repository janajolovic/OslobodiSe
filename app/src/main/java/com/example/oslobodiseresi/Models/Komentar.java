package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    private int id;
    @SerializedName("user")
    private Korisnik korisnik;
    private String sadrzaj;
    private Integer brojLajkova;
    private boolean lajkovan;

    public Komentar(int id, Korisnik korisnik, String sadrzaj, Integer brojLajkova, boolean lajkovan) {
        this.id = id;
        this.korisnik = korisnik;
        this.sadrzaj = sadrzaj;
        this.brojLajkova = brojLajkova;
        this.lajkovan = lajkovan;
    }

    public boolean isLajkovan() {
        return lajkovan;
    }

    public int getId() {
        return id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public Integer getBrojLajkova() {
        return brojLajkova;
    }

    public void setBrojLajkova(Integer brojLajkova) {
        this.brojLajkova = brojLajkova;
    }

    public void setLajkovan(boolean lajkovan) {
        this.lajkovan = lajkovan;
    }

    @Override
    public String toString() {
        return "Komentar{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", sadrzaj='" + sadrzaj + '\'' +
                ", brojLajkova=" + brojLajkova +
                ", lajkovan=" + lajkovan +
                '}';
    }
}
