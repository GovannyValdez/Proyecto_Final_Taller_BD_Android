package com.example.proyecto_final_taller_bd_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import Aeropuerto_BD.Conexion_BD;
import entities.Empleados;

public class AgregarEmpleadoActivity extends AppCompatActivity {

    private TextInputEditText etSSN, etNombre, etApellidoPaterno, etApellidoMaterno,
            etDireccion, etTelefono, etSalario, etMembresia;

    private Button btnGuardar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);

        inicializarVistas();
        configurarBotones();
    }

    private void inicializarVistas() {

        etSSN = findViewById(R.id.etSSN);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        etSalario = findViewById(R.id.etSalario);
        etMembresia = findViewById(R.id.etMembresia);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
    }

    private void configurarBotones() {

        btnGuardar.setOnClickListener(v -> guardarEmpleado());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarEmpleado() {

        String ssn = etSSN.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String apPaterno = etApellidoPaterno.getText().toString().trim();
        String apMaterno = etApellidoMaterno.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String salarioStr = etSalario.getText().toString().trim();
        String membresia = etMembresia.getText().toString().trim();

        // Validar campos obligatorios
        if (ssn.isEmpty() || nombre.isEmpty() || apPaterno.isEmpty() ||
                salarioStr.isEmpty() || membresia.isEmpty()) {

            Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_LONG).show();
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (Exception e) {
            Toast.makeText(this, "El salario no es válido", Toast.LENGTH_LONG).show();
            return;
        }

        Empleados empleado = new Empleados(
                ssn, nombre, apPaterno, apMaterno,
                direccion, telefono, salario, membresia
        );

        // Guardar en la BD (HILO SECUNDARIO)
        new Thread(() -> {

            Conexion_BD db = Conexion_BD.getAppDatabase(getApplicationContext());

            db.empleadosDAO().agregarEmpleado(empleado);

            runOnUiThread(() -> {
                Toast.makeText(this, "Empleado agregado correctamente", Toast.LENGTH_LONG).show();
                finish();
            });

        }).start();
    }
}
