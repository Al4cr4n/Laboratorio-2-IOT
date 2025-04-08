package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class EstadisticasActivity extends AppCompatActivity {

    private TextView historialTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        historialTxt = findViewById(R.id.txtHistorial);
        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);

        List<String> historial = Historial.getHistorial();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < historial.size(); i++) {
            String resultado = historial.get(i);
            builder.append("Juego ").append(i + 1).append(": ");

            if (resultado.equals("Canceló")) {
                builder.append("Canceló");
            } else {
                builder.append(resultado);
            }

            builder.append("\n");
        }
        historialTxt.setText(builder.toString());

        btnNuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstadisticasActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
