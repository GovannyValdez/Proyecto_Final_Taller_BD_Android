package com.example.proyecto_final_taller_bd_android;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import Aeropuerto_BD.Conexion_BD;
import entities.Empleados;

public class ConsultarEmpleadoActivity extends AppCompatActivity {

    private TextInputEditText etBuscar;
    private Button btnBuscar, btnMostrarTodos, btnCancelar;
    private ListView lvEmpleados;
    private TextView tvContador, tvSinResultados;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empleado);

        inicializarVistas();
        configurarEventos();
    }

    private void inicializarVistas() {

        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnMostrarTodos = findViewById(R.id.btnMostrarTodos);
        btnCancelar = findViewById(R.id.btnCancelar);

        lvEmpleados = findViewById(R.id.lvEmpleados);
        tvContador = findViewById(R.id.tvContador);
        tvSinResultados = findViewById(R.id.tvSinResultados);
    }

    private void configurarEventos() {

        btnBuscar.setOnClickListener(v -> buscarEmpleado());

        btnMostrarTodos.setOnClickListener(v -> mostrarTodos());

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void buscarEmpleado() {

        String filtro = etBuscar.getText().toString().trim();

        if (filtro.isEmpty()) {
            Toast.makeText(this, "Ingrese SSN o nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {

            Conexion_BD db = Conexion_BD.getAppDatabase(getApplicationContext());
            List<Empleados> lista = db.empleadosDAO().buscarPorCoincidencia(filtro);

            runOnUiThread(() -> mostrarResultados(lista));
        }).start();
    }

    private void mostrarTodos() {

        new Thread(() -> {

            Conexion_BD db = Conexion_BD.getAppDatabase(getApplicationContext());
            List<Empleados> lista = db.empleadosDAO().mostrarTodos();

            runOnUiThread(() -> mostrarResultados(lista));

        }).start();
    }

    private void mostrarResultados(List<Empleados> lista) {

        if (lista == null || lista.isEmpty()) {

            lvEmpleados.setVisibility(View.GONE);
            tvSinResultados.setVisibility(View.VISIBLE);
            tvContador.setText("0 empleados encontrados");

            return;
        }

        tvSinResultados.setVisibility(View.GONE);
        lvEmpleados.setVisibility(View.VISIBLE);

        // Convertir objetos a texto legible
        String[] datos = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            Empleados emp = lista.get(i);
            datos[i] = "SSN: " + emp.getSsn() + "\nNombre: " + emp.getNombre() +
                    "\nMembresía: " + emp.getNumeroMembresiaSindicato();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        lvEmpleados.setAdapter(adapter);

        tvContador.setText(lista.size() + " empleados encontrados");
    }
}

