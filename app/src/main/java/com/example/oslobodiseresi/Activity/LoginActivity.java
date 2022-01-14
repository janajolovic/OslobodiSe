package com.example.oslobodiseresi.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends ToolbarNavigacijaSetup {

    private EditText Email;
    private EditText Lozinka;
    private Button dugme;
    private TextView loginPogresno;
    private TextView loginToRegister;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_login);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        Email = findViewById(R.id.emailLogin);
        Lozinka = findViewById(R.id.lozinkaLogin);
        dugme = findViewById(R.id.prijavaBtn);
        loginToRegister = findViewById(R.id.txtLoginToRegister);
        loginPogresno = findViewById(R.id.loginPogresno);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableLiveData<Korisnik> k = UserRepository.getInstance(MainApplication.apiManager).Login(new LoginModel(Email.getText().toString(), Lozinka.getText().toString()));
                k.observe(LoginActivity.this, new Observer<Korisnik>() {
                    @Override
                    public void onChanged(Korisnik korisnik) {
                        if(k.getValue()!=null){
                            Utils.getInstance().setKorisnik(k.getValue());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            loginPogresno.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistracijaActivity.class);
                startActivity(intent);
            }
        });
    }
}