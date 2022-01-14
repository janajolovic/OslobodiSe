package com.example.oslobodiseresi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private Korisnik korisnik;
    private ArrayList<Kategorija> kategorije;
    private ArrayList<Grad> gradovi;
    public static final String PREFERENCES = "OslobodiSePrefs" ;
    public static final String KorisnikKey = "Korisnik";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private static Utils instance;

    private  Utils(){

    }

    private Utils(Context context){
        gson = new GsonBuilder().serializeNulls().create();
        sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Type type = new TypeToken<Korisnik>(){}.getType();
        korisnik = gson.fromJson(sharedpreferences.getString(KorisnikKey, null), type);
        initData();
    }

    public void initData()
    {

    }

    public void SacuvajKorisnika(Korisnik korisnik){
        editor.putString(KorisnikKey, gson.toJson(korisnik));
        this.korisnik = korisnik;
        editor.commit();
    }

    public static Utils getInstance(Context context){
        if(instance == null)
            instance = new Utils(context);
        return instance;
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

}
