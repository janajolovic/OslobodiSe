package com.example.oslobodiseresi.Retrofit;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.Komentar;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestInterface {
    @POST("users/register")
    Call<Korisnik> register(@Body RegistarModel korisnik);

    @POST("users/login")
    Call<Korisnik> login(@Body LoginModel korisnik);

    @GET("items/all")
    Call<ArrayList<Item>>getAllItems();

    @POST("items/post")
    Call<Item> postItem(@Body ItemPostModel model);

    @GET("items/kategorije/all")
    Call<ArrayList<Kategorija>> getAllKategorije();

    @GET("items/gradovi/all")
    Call<ArrayList<Grad>> getAllGradovi();

    @GET("items/{Id}")
    Call<Item> getItem(@Path("Id") int Id);

    @GET("items/user/{userId}")
    Call<ArrayList<Item>> getItemsFromUser(@Path("userId") String Id);

    @GET("items/grad/{gradId}")
    Call<ArrayList<Item>> getItemsFromGrad(@Path("gradId") int gradId);

    @GET("items/kategorije/{kategorijaId}")
    Call<ArrayList<Item>> getItemsFromKategorija(@Path("kategorijaId") int kategorijaId);

    @GET("items/kategorija/grad/{kategorijaId}/{gradId}")
    Call<ArrayList<Item>> getItemsFromKategorijaGrad(@Path("kategorijaId") int kategorijaId, @Path("gradId") int gradId);

    @GET("users/omiljenioglasi/{UserId}")
    Call<ArrayList<Item>> getOmiljeniOglasiFromUser(@Path("UserId") String userId);

    @PATCH("users/omiljenioglasi/dodaj/{UserId}/{OglasId}")
    Call<String> dodajOmiljeniOglas(@Path("UserId") String userId, @Path("OglasId") int oglasId);

    @PATCH("users/omiljenioglasi/izbrisi/{UserId}/{OglasId}")
    Call<String> IzbrisiOmiljeniOglas(@Path("UserId") String userId, @Path("OglasId") int oglasId);

    @GET("users/{Id}")
    Call<Korisnik> GetKorisnikById(@Path("Id") String Id);

    @GET("users/omiljenioglasi/proveri/{UserId}/{OglasId}")
    Call<Boolean> ProveriOmiljeniOglas(@Path("UserId") String UserId, @Path("OglasId") int OglasId);

    @PATCH("users/dodajocenu/{UserId}/{RaterId}/{ocena}")
    Call<Float> DodajOcenu(@Path("UserId") String UserId, @Path("RaterId") String RaterId, @Path("ocena") float ocena);

    @PATCH("items/komentar/dodaj/{OglasId}/{UserId}/{tekst}")
    Call<Komentar> DodajKomentar(@Path("OglasId") int OglasId, @Path("UserId") String UserId, @Path("tekst") String tekst);

    @Multipart
    @POST("slike/post")
    Call<ResponseBody> PostSlika(@Part MultipartBody.Part image);

    @DELETE("/items/komentar/izbrisi/{KomentarId}")
    Call<String> IzbrisiKoemntar(@Path("KomentarId") int KomentarId);

    @HTTP(method = "DELETE", path = "items/delete", hasBody = true)
    Call<String> deleteItem(@Body int Id);
}
