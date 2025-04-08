package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEMA = "tema";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSoftware = findViewById(R.id.btnSoftware);
        Button btnCiberseguridad = findViewById(R.id.btnCiberseguridad);
        Button btnOpticas = findViewById(R.id.btnOpticas);

        btnSoftware.setOnClickListener(v -> abrirJuego("Software"));
        btnCiberseguridad.setOnClickListener(v -> abrirJuego("Ciberseguridad"));
        btnOpticas.setOnClickListener(v -> abrirJuego("Ópticas"));
    }

    private void abrirJuego(String tema) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(EXTRA_TEMA, tema);
        startActivity(intent);
    }
}
