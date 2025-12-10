package com.example.proyecto_final_taller_bd_android;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuInicioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        Button btnEmpleados = findViewById(R.id.btnEmpleados);
        Button btnModelos = findViewById(R.id.btnModelos);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnEmpleados.setOnClickListener(view -> {
            // TODO: agregar activity empleados
        });

        btnModelos.setOnClickListener(view -> {
            // TODO: agregar activity modelos
        });

        btnCerrarSesion.setOnClickListener(view -> finish());
    }
}

