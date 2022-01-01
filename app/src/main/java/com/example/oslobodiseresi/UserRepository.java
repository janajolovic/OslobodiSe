package com.example.oslobodiseresi;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static volatile UserRepository instance;
    private final ApiManager apiManager;

    private final MutableLiveData<Korisnik> korisnik = new MutableLiveData<>();

    private UserRepository(ApiManager apiManager){
        this.apiManager = apiManager;
    }

    public static UserRepository getInstance(ApiManager apiManager){
        if(instance == null){
            instance = new UserRepository(apiManager);
        }
        return instance;
    }

    public MutableLiveData<Korisnik> Register(RegistarModel model){
        apiManager.RegisterUser(model, new Callback<Korisnik>() {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                if(response.isSuccessful()){
                    korisnik.setValue(response.body());
                }else{
                    Log.println(Log.ERROR, "[Greska1]",response.message());
                    korisnik.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska]",t.getMessage());
                korisnik.setValue(null);
            }
        });
        return korisnik;
    }

    public MutableLiveData<Korisnik> Login(LoginModel model) {
        apiManager.LoginUser(model, new Callback<Korisnik>() {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                if (response.isSuccessful()) {
                    korisnik.setValue(response.body());
                }else{
                    Log.println(Log.ERROR, "[Greska1]",response.message());
                    korisnik.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska]",t.getMessage());
                korisnik.setValue(null);
            }
        });
        return korisnik;
    }
}
