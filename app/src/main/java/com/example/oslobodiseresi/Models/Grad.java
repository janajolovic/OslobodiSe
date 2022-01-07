package com.example.oslobodiseresi.Models;

import com.google.gson.annotations.SerializedName;

public class Grad {
    @SerializedName("id")
    private int Id;
    @SerializedName("naziv")
    private  String Naziv;

    public int getId() {
        return Id;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    @Override
    public String toString() {
        return "Grad{" +
                "Naziv='" + Naziv + '\'' +
                '}';
    }
}
