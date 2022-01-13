package com.example.oslobodiseresi;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Retrofit.ItemRepository;

import java.util.ArrayList;

public class Utils {
    private Korisnik korisnik;
    private ArrayList<Kategorija> kategorije;
    private ArrayList<Grad> gradovi;

    private static Utils instance;

    private Utils(){
        korisnik = null;
        initData();
    }

    public void initData()
    {
      MutableLiveData<ArrayList<Kategorija>> kategorijaMLD = ItemRepository.getInstance(MainApplication.apiManager).getAllKategorije();
      MutableLiveData<ArrayList<Grad>> gradMLD = ItemRepository.getInstance(MainApplication.apiManager).getAllGradovi();

      //kategorijaMLD.observe();
    }


    public static Utils getInstance(){
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    public boolean jeUlogovan() {
        return korisnik != null;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

}
