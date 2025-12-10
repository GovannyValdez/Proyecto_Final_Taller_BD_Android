package com.example.proyecto_final_taller_bd_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuEmpleadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empleados);

        Button btnAgregar = findViewById(R.id.btnAgregarEmpleado);
        Button btnConsultar = findViewById(R.id.btnConsultarEmpleado);
        Button btnModificar = findViewById(R.id.btnModificarEmpleado);
        Button btnEliminar = findViewById(R.id.btnEliminarEmpleado);
        Button btnRegresar = findViewById(R.id.btnRegresar);


        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuEmpleadosActivity.this, AgregarEmpleadoActivity.class);
            startActivity(intent);
        });


        btnConsultar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuEmpleadosActivity.this, ConsultarEmpleadoActivity.class);
            startActivity(intent);
        });


        btnModificar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuEmpleadosActivity.this, ModificarEmpleadoActivity.class);
            startActivity(intent);
        });


        btnEliminar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuEmpleadosActivity.this, EliminarEmpleadoActivity.class);
            startActivity(intent);
        });


        btnRegresar.setOnClickListener(v -> {
            finish(); // Simplemente cierra este activity
        });
    }
}

