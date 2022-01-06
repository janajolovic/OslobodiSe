package com.example.oslobodiseresi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

        setToolbar(this);

        Email = findViewById(R.id.emailLogin);
        Lozinka = findViewById(R.id.lozinkaLogin);
        dugme = findViewById(R.id.prijavaBtn);
        loginToRegister = findViewById(R.id.txtLoginToRegister);
        loginPogresno = findViewById(R.id.loginPogresno);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableLiveData<Korisnik> k = UserRepository.getInstance(MainApplication.apiManager).Login(new LoginModel(Email.getText().toString(), Lozinka.getText().toString()));
                k.observe(LoginActivity.this, funk->{
                    if(k.getValue()!=null){
                        Toast.makeText(LoginActivity.this, "Korisnik je "+k.getValue().getIme(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Utils.getInstance().setKorisnik(k.getValue());
                        Utils.getInstance().setJelUlogovan(1);
                        startActivity(intent);
                    }
                    else
                        loginPogresno.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.pocetna:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            //TODO: dovrsiti
            case R.id.mojProfil:
                intent = new Intent(this, MojProfilActivity.class);
                startActivity(intent);
                break;
            case R.id.mojiOglasi:
                intent = new Intent(this, MojiOglasiActivity.class);
                startActivity(intent);
                break;
            case R.id.omiljeniOglasi:
                intent = new Intent(this, OmiljeniOglasiActivity.class);
                startActivity(intent);
                break;
        }
        //close navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void setToolbar(Context context){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView back;
        ImageView hamburger;
        ImageView  imgPrijava;
        //prijava
        imgPrijava = findViewById(R.id.imgProfil);
        imgPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
        //todo search

        //hamburger
        hamburger = findViewById(R.id.imgHamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer((GravityCompat.START));
            }
        });
        //back
        back = navigationView.getHeaderView(0).findViewById(R.id.navBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
}