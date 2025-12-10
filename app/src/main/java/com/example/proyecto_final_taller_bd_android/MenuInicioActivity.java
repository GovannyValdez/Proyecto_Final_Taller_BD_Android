package com.example.proyecto_final_taller_bd_android;


import android.content.Intent;
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
            Intent intent = new Intent(MenuInicioActivity.this, MenuEmpleadosActivity.class);
            startActivity(intent);
        });

        btnModelos.setOnClickListener(view -> {
            // TODO: agregar activity modelos
            Intent intent = new Intent(MenuInicioActivity.this, MenuModelosActivity.class);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(view -> finish());
    }
}

