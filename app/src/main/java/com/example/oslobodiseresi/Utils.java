package com.example.oslobodiseresi;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Artikal> artikli;

    private Utils(){
        if(instance == null)
        {
            artikli = new ArrayList<>();
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

    //neefikasno, ali nema veze
    public static Artikal getArtikalById(int id)
    {
        for(int i = 0; i<artikli.size(); i++) {
            if(id == artikli.get(i).getId())
                return artikli.get(i);
        }
        return null;
    }
}
