package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

public class ItemPostModel {
    @SerializedName("naziv")
    private String Naziv;
    @SerializedName("opis")
    private String Opis;
    @SerializedName("kategorijaId")
    private int KategorijaId;
    @SerializedName("gradId")
    private int GradId;
    @SerializedName("userId")
    private String UserId;
    private String slika;

    public ItemPostModel(String naziv, String opis, int kategorijaId, int gradId, String userId, String slika) {
        Naziv = naziv;
        Opis = opis;
        KategorijaId = kategorijaId;
        GradId = gradId;
        UserId = userId;
        this.slika = slika;
    }

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
