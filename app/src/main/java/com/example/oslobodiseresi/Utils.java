package com.example.oslobodiseresi;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;

import java.util.ArrayList;

public class Utils {
    private int jelUlogovan;
    private Korisnik korisnik;

    private static Utils instance;

    private static ArrayList<Item> artikli;
    private static ArrayList<Korisnik> korisnici;
    private Utils(){
        jelUlogovan = 0;
        korisnik = null;
        Log.println(Log.ERROR, "[Greska]","Konstruktor je pozvan");
        artikli = new ArrayList<>();
        korisnici = new ArrayList<>();
        initData();
    }

    public void initData()
    {
        Log.println(Log.ERROR, "[Greska]","init data je pozvan");
       MutableLiveData<ArrayList<Item>> mld = new MutableLiveData<>();
       mld = ItemRepository.getInstance(MainApplication.apiManager).getAllItems();
       if(mld != null)
        artikli = mld.getValue();

//       if(artikli.size() != 0)
//           Log.println(Log.ERROR, "[Greska]","Ima data");
//        else
//            Log.println(Log.ERROR, "[Greska]","nema data");
    }

    public static ArrayList<Item> getArtikli() {
        return artikli;
    }

    public static ArrayList<Korisnik> getKorisnici() { return korisnici; }

    public boolean addToKorisnici(Korisnik k){
        return korisnici.add(k);
    }

    public boolean addToArtikli(Item a){
        return artikli.add(a);
    }
//
//    public Korisnik prijaviKorisnika(String email, String lozinka)
//    {
//        for(int i=0;i<korisnici.size();i++){
//            if(korisnici.get(i).getEmail().equals(email) && korisnici.get(i).getLozinka().equals(lozinka))
//                return korisnici.get(i);
//        }
//        return null;
//    }

    //neefikasno, ali nema veze i bice jos gore
    public static Item getArtikalById(int id)
    {
        for(int i = 0; i<artikli.size(); i++) {
            if(id == artikli.get(i).getId())
                return artikli.get(i);
        }
        return null;
    }

    public static Utils getInstance(){
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    public int getJelUlogovan() {
        return jelUlogovan;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public void setJelUlogovan(int jelUlogovan) {
        this.jelUlogovan = jelUlogovan;
    }

}
