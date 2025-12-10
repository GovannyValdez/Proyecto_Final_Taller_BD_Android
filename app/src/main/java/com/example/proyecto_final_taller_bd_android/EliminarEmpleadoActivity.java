package com.example.proyecto_final_taller_bd_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import Aeropuerto_BD.Conexion_BD;
import Controlers.EmpleadosDAO;
import entities.Empleados;

public class EliminarEmpleadoActivity extends AppCompatActivity {

    TextInputEditText etBuscar;
    Button btnBuscar, btnEliminar, btnCancelar;

    TextView tvSSN, tvNombre, tvApellidos, tvDireccion, tvTelefono, tvSalario, tvMembresia;
    View cardEmpleado, tvNoEncontrado;

    EmpleadosDAO empleadosDAO;
    Empleados empleadoEncontrado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_empleado);

        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnCancelar = findViewById(R.id.btnCancelar);

        tvSSN = findViewById(R.id.tvSSN);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvSalario = findViewById(R.id.tvSalario);
        tvMembresia = findViewById(R.id.tvMembresia);

        cardEmpleado = findViewById(R.id.cardEmpleado);
        tvNoEncontrado = findViewById(R.id.tvNoEncontrado);

        btnEliminar.setEnabled(false);

        empleadosDAO = Conexion_BD.getAppDatabase(getApplicationContext()).empleadosDAO();

        btnBuscar.setOnClickListener(v -> buscarEmpleado());
        btnEliminar.setOnClickListener(v -> eliminarEmpleado());
        btnCancelar.setOnClickListener(v -> finish());
    }


    private void buscarEmpleado() {

        String texto = etBuscar.getText().toString().trim();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingrese SSN o Nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {

            List<Empleados> lista = empleadosDAO.buscarPorCoincidencia(texto);

            runOnUiThread(() -> {
                if (lista.isEmpty()) {
                    mostrarNoEncontrado();
                } else {
                    empleadoEncontrado = lista.get(0);
                    mostrarEmpleado(empleadoEncontrado);
                }
            });

        }).start();
    }



    private void mostrarEmpleado(Empleados e) {

        tvNoEncontrado.setVisibility(View.GONE);
        cardEmpleado.setVisibility(View.VISIBLE);
        btnEliminar.setEnabled(true);

        tvSSN.setText("SSN: " + e.getSsn());
        tvNombre.setText("Nombre: " + e.getNombre());

        String apellidos = e.getApellidoPaterno();
        if (e.getApellidoMaterno() != null && !e.getApellidoMaterno().isEmpty()) {
            apellidos += " " + e.getApellidoMaterno();
        }

        tvApellidos.setText("Apellidos: " + apellidos);
        tvDireccion.setText("Dirección: " + e.getDireccion());
        tvTelefono.setText("Teléfono: " + e.getTelefono());
        tvSalario.setText("Salario: " + e.getSalario());
        tvMembresia.setText("Membresía: " + e.getNumeroMembresiaSindicato());
    }



    private void mostrarNoEncontrado() {
        cardEmpleado.setVisibility(View.GONE);
        tvNoEncontrado.setVisibility(View.VISIBLE);
        btnEliminar.setEnabled(false);
        empleadoEncontrado = null;
    }



    private void eliminarEmpleado() {

        if (empleadoEncontrado == null) {
            Toast.makeText(this, "Primero busque un empleado", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {

            empleadosDAO.eliminarEmpleado(empleadoEncontrado);

            runOnUiThread(() -> {
                Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_SHORT).show();
                finish();
            });

        }).start();
    }
}
