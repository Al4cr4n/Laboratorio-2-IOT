// MainActivity2.java
package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<String> oracionActual;
    private List<Button> botones = new ArrayList<>();
    private List<String> palabrasSeleccionadas = new ArrayList<>();
    private int intentos = 0;
    private long tiempoInicio;
    private GridLayout gridPalabras;
    private Button btnJugar;
    private TextView txtResultado;
    private static final int MAX_INTENTOS = 3;
    private boolean juegoTerminado = false;
    private boolean juegoIniciado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gridPalabras = findViewById(R.id.gridPalabras);
        btnJugar = findViewById(R.id.btnJugar);
        txtResultado = findViewById(R.id.txtResultado);

        mostrarBotonesJuego();

        btnJugar.setOnClickListener(v -> {
            if (!juegoIniciado) {
                iniciarJuego();
                btnJugar.setText("Nuevo Juego");
            } else {
                if (!juegoTerminado) {
                    Historial.agregarResultado("Canceló");
                }
                iniciarJuego();
            }
        });

        ImageView iconAtras = findViewById(R.id.iconAtras);
        iconAtras.setOnClickListener(v -> finish());

        ImageView iconEstadistica = findViewById(R.id.iconEstadistica);
        iconEstadistica.setOnClickListener(v -> abrirEstadisticas());
    }

    private void abrirEstadisticas() {
        Intent intent = new Intent(this, EstadisticasActivity.class);
        startActivity(intent);
    }

    private void mostrarBotonesJuego() {
        gridPalabras.removeAllViews();
        botones.clear();
        for (int i = 0; i < 12; i++) {
            Button btn = new Button(this);
            btn.setEnabled(false);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 300;
            params.height = 220;
            params.setMargins(16, 16, 16, 16);
            btn.setLayoutParams(params);
            btn.setTextSize(18);
            gridPalabras.addView(btn);
            botones.add(btn);
        }
    }

    private void iniciarJuego() {
        txtResultado.setText("");
        gridPalabras.removeAllViews();
        botones.clear();
        palabrasSeleccionadas.clear();
        intentos = 0;
        juegoTerminado = false;
        juegoIniciado = true;

        tiempoInicio = SystemClock.elapsedRealtime();

        String tema = getIntent().getStringExtra(MainActivity.EXTRA_TEMA);
        oracionActual = obtenerOracionAleatoria(tema);
        List<String> palabrasDesordenadas = new ArrayList<>(oracionActual);
        Collections.shuffle(palabrasDesordenadas);

        for (String palabra : palabrasDesordenadas) {
            Button btn = new Button(this);
            btn.setText("");
            btn.setTag(palabra);
            btn.setOnClickListener(view -> manejarSeleccion(btn));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 300;
            params.height = 220;
            params.setMargins(16, 16, 16, 16);
            btn.setLayoutParams(params);
            btn.setTextSize(18);
            gridPalabras.addView(btn);
            botones.add(btn);
        }
    }

    private void manejarSeleccion(Button btn) {
        if (juegoTerminado) return;

        int index = palabrasSeleccionadas.size();
        String palabraCorrecta = oracionActual.get(index);
        if (btn.getTag().toString().equals(palabraCorrecta)) {
            btn.setText(palabraCorrecta);
            palabrasSeleccionadas.add(palabraCorrecta);
            btn.setEnabled(false);

            if (palabrasSeleccionadas.size() == oracionActual.size()) {
                long tiempoFinal = SystemClock.elapsedRealtime();
                long duracion = (tiempoFinal - tiempoInicio) / 1000;
                String resultado = "Ganó / Terminó en " + duracion + "s\nIntentos: " + intentos;
                txtResultado.setText(resultado);
                Historial.agregarResultado(resultado);
                juegoTerminado = true;
            }
        } else {
            intentos++;
            if (intentos >= MAX_INTENTOS) {
                long tiempoFinal = SystemClock.elapsedRealtime();
                long duracion = (tiempoFinal - tiempoInicio) / 1000;
                String resultado = "Perdió / Terminó en " + duracion + "s";
                txtResultado.setText(resultado);
                Historial.agregarResultado(resultado);
                juegoTerminado = true;
            } else {
                Toast.makeText(this, "Incorrecto. Te quedan " + (MAX_INTENTOS - intentos) + " intentos.", Toast.LENGTH_SHORT).show();
                reiniciarSeleccion();
            }
        }
    }

    private void reiniciarSeleccion() {
        palabrasSeleccionadas.clear();
        for (Button btn : botones) {
            btn.setText("");
            btn.setEnabled(true);
        }
    }

    private List<String> obtenerOracionAleatoria(String tema) {
        List<List<String>> opciones = new ArrayList<>();
        if ("Ópticas".equals(tema)) {
            opciones.add(List.of("La", "fibra", "óptica", "envía", "datos", "a", "gran", "velocidad", "evitando", "cualquier", "interferencia", "eléctrica"));
            opciones.add(List.of("Los", "amplificadores", "EDFA", "mejoran", "la", "señal", "óptica", "en", "redes", "de", "larga", "distancia"));
        } else if ("Ciberseguridad".equals(tema)) {
            opciones.add(List.of("Una", "VPN", "encripta", "tu", "conexión", "para", "navegar", "de", "forma", "anónima", "y", "segura"));
            opciones.add(List.of("El", "ataque", "DDoS", "satura", "servidores", "con", "tráfico", "falso", "y", "causa", "caídas", "masivas"));
        } else if ("Software".equals(tema)) {
            opciones.add(List.of("Los", "fragments", "reutilizan", "partes", "de", "pantalla", "en", "distintas", "actividades", "de", "la", "app"));
            opciones.add(List.of("Los", "intents", "permiten", "acceder", "a", "apps", "como", "la", "cámara", "o", "WhatsApp", "directamente"));
        }
        Collections.shuffle(opciones);
        return opciones.get(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_estadisticas) {
            abrirEstadisticas();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
