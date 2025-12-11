package com.example.proyecto_final_taller_bd_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuModelosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_modelos);

        Button btnAgregar = findViewById(R.id.btnAgregarModelo);
        Button btnConsultar = findViewById(R.id.btnConsultarModelo);
        Button btnModificar = findViewById(R.id.btnModificarModelo);
        Button btnEliminar = findViewById(R.id.btnEliminarModelo);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuModelosActivity.this, AgregarModeloActivity.class);
            startActivity(intent);
        });

        btnConsultar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuModelosActivity.this, ConsultarModeloActivity.class);
            startActivity(intent);
        });

        btnModificar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuModelosActivity.this, ModificarModeloActivity.class);
            startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuModelosActivity.this, EliminarModeloActivity.class);
            startActivity(intent);
        });

        btnRegresar.setOnClickListener(v -> {
            finish();
        });
    }
}

