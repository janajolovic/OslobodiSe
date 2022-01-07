package com.example.oslobodiseresi.Models;

public class ItemPostModel {
    private String Naziv;
    private String Opis;
    private int KategorijaId;
    private int GradId;
    private String UserId;

    //region Getters and Setters
    public String getNaziv() {
        return Naziv;
    }

    public String getOpis() {
        return Opis;
    }

    public int getKategorijaId() {
        return KategorijaId;
    }

    public int getGradId() {
        return GradId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public void setKategorijaId(int kategorijaId) {
        KategorijaId = kategorijaId;
    }

    public void setGradId(int gradId) {
        GradId = gradId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
    //endregion
}
