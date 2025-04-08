package com.example.lab2;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    private static final List<String> resultados = new ArrayList<>();

    public static void agregarResultado(String resultado) {
        resultados.add(resultado);
    }

    public static List<String> getHistorial() {
        return resultados;
    }

    public static void reiniciar() {
        // No borra el historial, pero podrías usarlo si quieres limpiar todo en algún punto
    }
}