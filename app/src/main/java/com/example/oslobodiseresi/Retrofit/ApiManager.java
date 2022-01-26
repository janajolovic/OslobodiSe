package com.example.oslobodiseresi.Retrofit;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.Komentar;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;
import com.example.oslobodiseresi.Models.UploadImage;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ApiManager {
    private static RestInterface service;
    private static ApiManager apiManager;

    private ApiManager(){
        service = RetrofitService.Create();
    }
    public static ApiManager getInstance(){
        if(apiManager == null) {
            apiManager = new ApiManager();
        }
        return  apiManager;
    }

    public void RegisterUser(RegistarModel model, Callback<Korisnik> callback){
        Call<Korisnik> registerCall = service.register(model);
        registerCall.enqueue(callback);
    }

    public void LoginUser(LoginModel model, Callback<Korisnik> callback) {
        Call<Korisnik> loginCall = service.login(model);
        loginCall.enqueue(callback);
    }

    public void GetAllItems(Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> allItems = service.getAllItems();
        allItems.enqueue(callback);
    }

    public void PostItem(ItemPostModel model, Callback<Item> callback){
        Call<Item> postItem = service.postItem(model);
        postItem.enqueue(callback);
    }

    public void GetAllKategorije(Callback<ArrayList<Kategorija>> callback){
        Call<ArrayList<Kategorija>> getAllKategorije = service.getAllKategorije();
        getAllKategorije.enqueue(callback);
    }

    public void GetAllGradovi(Callback<ArrayList<Grad>> callback){
        Call<ArrayList<Grad>> getAllGradovi = service.getAllGradovi();
        getAllGradovi.enqueue(callback);
    }

    public void GetItem(int Id, Callback<Item> callback){
        Call<Item> getItem = service.getItem(Id);
        getItem.enqueue(callback);
    }

    public void GetItemsFromUser(String Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromUser = service.getItemsFromUser(Id);
        getItemsFromUser.enqueue(callback);
    }

    public void GetItemsFromGrad(int Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromGrad = service.getItemsFromGrad(Id);
        getItemsFromGrad.enqueue(callback);
    }

    public void GetItemsFromKategorija(int Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromKategorija = service.getItemsFromKategorija(Id);
        getItemsFromKategorija.enqueue(callback);
    }

    public void GetItemsFromKategorijaGrad(int kategorijaId, int gradId,  Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromKategorijaGrad = service.getItemsFromKategorijaGrad(kategorijaId, gradId);
        getItemsFromKategorijaGrad.enqueue(callback);
    }

    public void DeleteItem(int Id, Callback<String> callback){
        Call<String> deleteItem = service.deleteItem(Id);
        deleteItem.enqueue(callback);
    }

    public void GetOmiljeniOglasiFromUser(String UserId, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getOmiljeniOglasiFromUser = service.getOmiljeniOglasiFromUser(UserId);
        getOmiljeniOglasiFromUser.enqueue(callback);
    }

    public void DodajOmiljeniOglas(String UserId, int OglasId, Callback<String> callback){
        Call<String> dodajOmiljeniOglas = service.dodajOmiljeniOglas(UserId, OglasId);
        dodajOmiljeniOglas.enqueue(callback);
    }

    public void GetKorisnikById(String Id, Callback<Korisnik> callback){
        Call<Korisnik> getKorisnikById = service.GetKorisnikById(Id);
        getKorisnikById.enqueue(callback);
    }

    public void IzbrisiOmiljeniOglas(String UserId, int OglasId, Callback<String> callback){
        Call<String> izbrisiOmiljeniOglas = service.IzbrisiOmiljeniOglas(UserId, OglasId);
        izbrisiOmiljeniOglas.enqueue(callback);
    }

    public void ProveriOmiljeniOglas(String UserId, int OglasId, Callback<Boolean> callback){
        Call<Boolean> proveriOmiljeniOglas = service.ProveriOmiljeniOglas(UserId, OglasId);
        proveriOmiljeniOglas.enqueue(callback);
    }

    public void DodajOcenu(String UserId, String RaterId, float ocena, Callback<Float> callback){
        Call<Float> dodajOcenu = service.DodajOcenu(UserId, RaterId, ocena);
        dodajOcenu.enqueue(callback);
    }

    public void DodajKomentar(int OglasId, String UserId, String tekst, Callback<Komentar> callback){
        Call<Komentar> dodajKomentar = service.DodajKomentar(OglasId, UserId , tekst);
        dodajKomentar.enqueue(callback);
    }

    public void LajkujKomentar(int KomentarId, String UserId, Callback<String> callback){
        Call<String> lajkujKomentar = service.LajkujKomentar(KomentarId, UserId);
        lajkujKomentar.enqueue(callback);
    }

    public void IzbrisiKomentar(int KomentarId, Callback<String> callback){
        Call<String> izbrisiKomentar = service.IzbrisiKoemntar(KomentarId);
        izbrisiKomentar.enqueue(callback);
    }

    public void PostSlika(UploadImage slika, Callback<String> callback){
        Call<String> postSlika = service.PostSlika(slika);
        postSlika.enqueue(callback);
    }

    public void GetProfilna(String UserId, Callback<ResponseBody> callback){
        Call<ResponseBody> getProfilna = service.GetProfilna(UserId);
        getProfilna.enqueue(callback);
    }

    public void GetKomentariFromOglas(int OglasId, Callback<ArrayList<Komentar>> callback){
        Call<ArrayList<Komentar>> getKomentariFromOglas = service.GetKomentariFromOglas(OglasId);
        getKomentariFromOglas.enqueue(callback);
    }

}
