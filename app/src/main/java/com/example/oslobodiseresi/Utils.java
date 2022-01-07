package com.example.oslobodiseresi;

import java.util.ArrayList;

public class Utils {
    private int jelUlogovan;
    private Korisnik korisnik;

    private static Utils instance;

    private static ArrayList<Artikal> artikli;
    private static ArrayList<Korisnik> korisnici;
    private Utils(){
        jelUlogovan = 0;
        korisnik = null;

        artikli = new ArrayList<>();
        korisnici = new ArrayList<>();
        initData();
    }

    private void initData()
    {

    }

    public static ArrayList<Artikal> getArtikli() {
        return artikli;
    }

    public static ArrayList<Korisnik> getKorisnici() { return korisnici; }

    public boolean addToKorisnici(Korisnik k){
        return korisnici.add(k);
    }

    public boolean addToArtikli(Artikal a){
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
    public static Artikal getArtikalById(int id)
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
