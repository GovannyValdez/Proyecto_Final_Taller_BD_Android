package com.example.proyecto_final_taller_bd_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import Aeropuerto_BD.Conexion_BD;
import Controlers.ModelosDAO;
import entities.Modelos;

import java.util.List;

public class ModificarModeloActivity extends AppCompatActivity {

    private TextInputEditText etBuscar;
    private TextInputLayout tilBuscar;
    private Button btnBuscar;

    private TextInputEditText etModelNumber, etCapacidad, etPeso;
    private TextInputLayout tilCapacidad, tilPeso;
    private Button btnActualizar, btnCancelar;
    private TextView tvMensaje;

    private ModelosDAO modelosDAO;
    private Modelos modeloActual;
    private boolean modeloEncontrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_modelo);

        Conexion_BD db = Conexion_BD.getAppDatabase(this);
        modelosDAO = db.modelosDAO();

        etBuscar = findViewById(R.id.etBuscar);
        tilBuscar = findViewById(R.id.tilBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);

        etModelNumber = findViewById(R.id.etModelNumber);
        etCapacidad = findViewById(R.id.etCapacidad);
        etPeso = findViewById(R.id.etPeso);
        tilCapacidad = findViewById(R.id.tilCapacidad);
        tilPeso = findViewById(R.id.tilPeso);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);
        tvMensaje = findViewById(R.id.tvMensaje);

        deshabilitarCamposEdicion();
        btnActualizar.setEnabled(false);

        btnBuscar.setOnClickListener(v -> buscarModelo());
        btnActualizar.setOnClickListener(v -> actualizarModelo());
        btnCancelar.setOnClickListener(v -> finish());

        etBuscar.setOnClickListener(v -> {
            etBuscar.setText("");
            tilBuscar.setHelperText("Ingrese el número del modelo a editar");
            limpiarCamposEdicion();
        });
    }

    private void buscarModelo() {
        String modelNumber = etBuscar.getText().toString().trim();

        if (modelNumber.isEmpty()) {
            tilBuscar.setError("Ingrese un número de modelo");
            etBuscar.requestFocus();
            return;
        }

        tilBuscar.setError(null);
        tvMensaje.setVisibility(View.GONE);

        List<Modelos> resultados = modelosDAO.mostrarPorModelNumber(modelNumber);

        if (resultados.isEmpty()) {
            mostrarMensaje("❌ Modelo no encontrado", true);
            limpiarCamposEdicion();
            modeloEncontrado = false;
            btnActualizar.setEnabled(false);
        } else {
            modeloActual = resultados.get(0);
            llenarCamposConDatos(modeloActual);
            habilitarCamposEdicion();
            modeloEncontrado = true;
            btnActualizar.setEnabled(true);
            mostrarMensaje("✅ Modelo encontrado. Modifique los campos necesarios.", false);
        }
    }

    private void llenarCamposConDatos(Modelos modelo) {
        etModelNumber.setText(modelo.getModelNumber());
        etCapacidad.setText(String.valueOf(modelo.getCapacidad()));
        etPeso.setText(String.valueOf(modelo.getPeso()));

        etCapacidad.requestFocus();
    }

    private void limpiarCamposEdicion() {
        etModelNumber.setText("");
        etCapacidad.setText("");
        etPeso.setText("");
        deshabilitarCamposEdicion();
        modeloEncontrado = false;
        btnActualizar.setEnabled(false);
    }

    private void deshabilitarCamposEdicion() {
        etCapacidad.setEnabled(false);
        etPeso.setEnabled(false);
        tilCapacidad.setHelperText("Busque un modelo primero");
        tilPeso.setHelperText("Busque un modelo primero");
    }

    private void habilitarCamposEdicion() {
        etCapacidad.setEnabled(true);
        etPeso.setEnabled(true);
        tilCapacidad.setHelperText("Rango: 1 - 1000 pasajeros");
        tilPeso.setHelperText("Rango: 500 - 1,000,000 kg");
    }

    private void mostrarMensaje(String texto, boolean esError) {
        tvMensaje.setText(texto);
        tvMensaje.setVisibility(View.VISIBLE);
        if (esError) {
            tvMensaje.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            tvMensaje.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            tvMensaje.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            tvMensaje.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    private void actualizarModelo() {
        if (!modeloEncontrado || modeloActual == null) {
            Toast.makeText(this, "Primero busque un modelo", Toast.LENGTH_SHORT).show();
            return;
        }

        String capacidadStr = etCapacidad.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();

        if (capacidadStr.isEmpty()) {
            tilCapacidad.setError("Ingrese la capacidad");
            etCapacidad.requestFocus();
            return;
        }

        if (pesoStr.isEmpty()) {
            tilPeso.setError("Ingrese el peso");
            etPeso.requestFocus();
            return;
        }

        try {
            int nuevaCapacidad = Integer.parseInt(capacidadStr);
            double nuevoPeso = Double.parseDouble(pesoStr);

            if (nuevaCapacidad < 1 || nuevaCapacidad > 1000) {
                tilCapacidad.setError("Rango: 1-1000");
                etCapacidad.requestFocus();
                return;
            }

            if (nuevoPeso < 500 || nuevoPeso > 1000000) {
                tilPeso.setError("Rango: 500-1,000,000 kg");
                etPeso.requestFocus();
                return;
            }

            modeloActual.setCapacidad(nuevaCapacidad);
            modeloActual.setPeso(nuevoPeso);

            modelosDAO.actualizarModelo(modeloActual);

            Toast.makeText(this,
                    "✅ Modelo " + modeloActual.getModelNumber() + " actualizado",
                    Toast.LENGTH_SHORT).show();

            etBuscar.setText("");
            limpiarCamposEdicion();
            tvMensaje.setVisibility(View.GONE);
            modeloEncontrado = false;
            btnActualizar.setEnabled(false);

            etBuscar.requestFocus();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ Formato numérico inválido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
