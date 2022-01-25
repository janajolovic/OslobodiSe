package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Item {
    @SerializedName("id")
    private int Id;
    @SerializedName("naziv")
    private String Naziv;
    @SerializedName("opis")
    private String Opis;

    private Kategorija kategorija;
    @SerializedName("kategorijaId")
    private int KategorijaId;

    private Grad grad;
    @SerializedName("gradId")
    private int GradId;

    private Korisnik user;
    @SerializedName("userId")
    private String UserId;

    private ArrayList<Komentar> komentari;

    public Item(int id, String naziv, String opis, Kategorija kategorija, int kategorijaId, Grad grad, int gradId, Korisnik user, String userId, ArrayList<Komentar> komentari) {
        Id = id;
        Naziv = naziv;
        Opis = opis;
        this.kategorija = kategorija;
        KategorijaId = kategorijaId;
        this.grad = grad;
        GradId = gradId;
        this.user = user;
        UserId = userId;
        this.komentari = komentari;
    }

    //region Getters
    public int getId() {
        return Id;
    }

    public String getNaziv() {
        return Naziv;
    }

    public String getOpis() {
        return Opis;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public int getKategorijaId() {
        return KategorijaId;
    }

    public Grad getGrad() {
        return grad;
    }

    public int getGradId() {
        return GradId;
    }

    public Korisnik getUser() {
        return user;
    }

    public String getUserId() {
        return UserId;
    }

    public ArrayList<Komentar> getKomentari() {
        return komentari;
    }

    //endregion


    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", Naziv='" + Naziv + '\'' +
                ", Opis='" + Opis + '\'' +
                ", kategorija=" + kategorija +
                ", KategorijaId=" + KategorijaId +
                ", grad=" + grad +
                ", GradId=" + GradId +
                ", user=" + user +
                ", UserId='" + UserId + '\'' +
                '}';
    }
}
