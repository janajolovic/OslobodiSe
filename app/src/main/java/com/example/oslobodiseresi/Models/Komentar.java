package com.example.oslobodiseresi.Models;

public class Komentar {
    private int id;
    private Korisnik korisnik;
    private String sadrzaj;
    private Integer brojLajkova;
    private Integer brojDislajkova;

    public Komentar(int id, Korisnik korisnik, String sadrzaj, Integer brojLajkova, Integer brojDislajkova) {
        this.id = id;
        this.korisnik = korisnik;
        this.sadrzaj = sadrzaj;
        this.brojLajkova = brojLajkova;
        this.brojDislajkova = brojDislajkova;
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

    public Integer getBrojDislajkova() {
        return brojDislajkova;
    }
}
