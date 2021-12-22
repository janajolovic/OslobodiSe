package com.example.oslobodiseresi;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Artikal> artikli;
    private static ArrayList<Korisnik> korisnici;

    private Utils(){
        if(instance == null)
        {
            artikli = new ArrayList<>();
            korisnici = new ArrayList<>();
            initData();
        }
    }

    public static Utils getInstance(){
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    private void initData()
    {
        artikli.add(new Artikal(1, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 1"));
        artikli.add(new Artikal(2, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 2"));
        artikli.add(new Artikal(3, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 3"));
        artikli.add(new Artikal(4, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 4"));
        artikli.add(new Artikal(5, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 5"));
        artikli.add(new Artikal(6, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 6"));
        artikli.add(new Artikal(7, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 7"));
        artikli.add(new Artikal(8, "Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg", "Kasika 8"));
    }

    public static ArrayList<Artikal> getArtikli() {
        return artikli;
    }

    public static ArrayList<Korisnik> getKorisnici() { return korisnici; }

    public boolean addToKorisnici(Korisnik k){
        return korisnici.add(k);
    }

    public Korisnik prijaviKorisnika(String email, String lozinka)
    {
        for(int i=0;i<korisnici.size();i++){
            if(korisnici.get(i).getEmail().equals(email) && korisnici.get(i).getLozinka().equals(lozinka))
                return korisnici.get(i);
        }
        return null;
    }

    //neefikasno, ali nema veze i bice jos gore
    public static Artikal getArtikalById(int id)
    {
        for(int i = 0; i<artikli.size(); i++) {
            if(id == artikli.get(i).getId())
                return artikli.get(i);
        }
        return null;
    }
}
