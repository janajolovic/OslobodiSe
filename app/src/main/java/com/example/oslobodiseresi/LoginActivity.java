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

    private TextView LoginToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.emailLogin);
        Lozinka = findViewById(R.id.lozinkaLogin);
        dugme = findViewById(R.id.prijavaBtn);
        LoginToRegister = findViewById(R.id.txtLoginToRegister);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Korisnik k = Utils.getInstance().prijaviKorisnika(Email.getText().toString(), Lozinka.getText().toString());
                //TODO: poboljsati.
                if(k != null)
                    Toast.makeText(LoginActivity.this, k.getIme()+" "+k.getPrezime()+" je prijavljen.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "Pogresna lozinka ili email.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        LoginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistracijaActivity.class);
                startActivity(intent);
            }
        });


    }
}