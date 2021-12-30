package com.example.oslobodiseresi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Lozinka;
    private Button dugme;
    private TextView loginPogresno;
    private TextView loginToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.emailLogin);
        Lozinka = findViewById(R.id.lozinkaLogin);
        dugme = findViewById(R.id.prijavaBtn);
        loginToRegister = findViewById(R.id.txtLoginToRegister);
        loginPogresno = findViewById(R.id.loginPogresno);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Korisnik k = Utils.getInstance().prijaviKorisnika(Email.getText().toString(), Lozinka.getText().toString());
//                if(k != null) {
//                    loginPogresno.setVisibility(View.INVISIBLE);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    loginPogresno.setVisibility(View.VISIBLE);
//
//                }

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