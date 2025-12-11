package com.example.proyecto_final_taller_bd_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.textfield.TextInputEditText;

import Aeropuerto_BD.Conexion_BD;
import Controlers.ModelosDAO;
import entities.Modelos;

import java.util.List;

public class EliminarModeloActivity extends AppCompatActivity {

    private TextInputEditText etBuscar;
    private Button btnBuscar, btnEliminar, btnCancelar;
    private CardView cardModelo;
    private TextView tvModelNumber, tvCapacidad, tvPeso, tvNoEncontrado;
    private ModelosDAO modelosDAO;
    private Modelos modeloSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_modelo);

        Conexion_BD db = Conexion_BD.getAppDatabase(this);
        modelosDAO = db.modelosDAO();

        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnCancelar = findViewById(R.id.btnCancelar);
        cardModelo = findViewById(R.id.cardModelo);
        tvModelNumber = findViewById(R.id.tvModelNumber);
        tvCapacidad = findViewById(R.id.tvCapacidad);
        tvPeso = findViewById(R.id.tvPeso);
        tvNoEncontrado = findViewById(R.id.tvNoEncontrado);

        cardModelo.setVisibility(View.GONE);
        tvNoEncontrado.setVisibility(View.GONE);
        btnEliminar.setEnabled(false);

        btnBuscar.setOnClickListener(v -> buscarModelo());
        btnEliminar.setOnClickListener(v -> confirmarEliminacion());
        btnCancelar.setOnClickListener(v -> finish());

        etBuscar.setOnClickListener(v -> {
            etBuscar.setText("");
            cardModelo.setVisibility(View.GONE);
            tvNoEncontrado.setVisibility(View.GONE);
            btnEliminar.setEnabled(false);
        });
    }

    private void buscarModelo() {
        String modelNumber = etBuscar.getText().toString().trim();

        if (modelNumber.isEmpty()) {
            etBuscar.setError("Ingrese un número de modelo");
            etBuscar.requestFocus();
            return;
        }

        List<Modelos> resultados = modelosDAO.mostrarPorModelNumber(modelNumber);

        if (resultados.isEmpty()) {
            cardModelo.setVisibility(View.GONE);
            tvNoEncontrado.setVisibility(View.VISIBLE);
            btnEliminar.setEnabled(false);
            modeloSeleccionado = null;
        } else {
            modeloSeleccionado = resultados.get(0);
            mostrarDatosModelo(modeloSeleccionado);
            cardModelo.setVisibility(View.VISIBLE);
            tvNoEncontrado.setVisibility(View.GONE);
            btnEliminar.setEnabled(true);
        }
    }

    private void mostrarDatosModelo(Modelos modelo) {
        tvModelNumber.setText("Número de Modelo: " + modelo.getModelNumber());
        tvCapacidad.setText("Capacidad: " + modelo.getCapacidad() + " pasajeros");
        tvPeso.setText("Peso: " + String.format("%.2f", modelo.getPeso()) + " kg");
    }

    private void confirmarEliminacion() {
        if (modeloSeleccionado == null) {
            Toast.makeText(this, "Primero busque un modelo", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminación");
        builder.setMessage("¿Está seguro de eliminar el modelo " + modeloSeleccionado.getModelNumber() + "?\n\nEsta acción no se puede deshacer.");

        builder.setPositiveButton("ELIMINAR", (dialog, which) -> eliminarModelo());
        builder.setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarModelo() {
        if (modeloSeleccionado == null) return;

        try {
            modelosDAO.eliminarModeloPorModelNumber(modeloSeleccionado.getModelNumber());

            Toast.makeText(this, "✅ Modelo " + modeloSeleccionado.getModelNumber() + " eliminado", Toast.LENGTH_SHORT).show();

            etBuscar.setText("");
            cardModelo.setVisibility(View.GONE);
            btnEliminar.setEnabled(false);
            modeloSeleccionado = null;

        } catch (Exception e) {
            Toast.makeText(this, "❌ Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}