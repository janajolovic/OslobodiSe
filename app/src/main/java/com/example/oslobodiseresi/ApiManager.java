package com.example.oslobodiseresi;

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
        loginCall.enqueue((callback));
    }
}
