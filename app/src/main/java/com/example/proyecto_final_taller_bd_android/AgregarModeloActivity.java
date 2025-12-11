package com.example.proyecto_final_taller_bd_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import Aeropuerto_BD.Conexion_BD;
import Controlers.ModelosDAO;
import entities.Modelos;

public class AgregarModeloActivity extends AppCompatActivity {

    private TextInputEditText etModelNumber, etCapacidad, etPeso;
    private Button btnGuardar, btnCancelar;
    private ModelosDAO modelosDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_modelo);

        Conexion_BD db = Conexion_BD.getAppDatabase(this);
        modelosDAO = db.modelosDAO();

        etModelNumber = findViewById(R.id.etModelNumber);
        etCapacidad = findViewById(R.id.etCapacidad);
        etPeso = findViewById(R.id.etPeso);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(v -> agregarModelo());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void agregarModelo() {
        String modelNumber = etModelNumber.getText().toString().trim();
        String capacidadStr = etCapacidad.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();

        if (modelNumber.isEmpty()) {
            etModelNumber.setError("Ingrese el número de modelo");
            etModelNumber.requestFocus();
            return;
        }

        if (capacidadStr.isEmpty()) {
            etCapacidad.setError("Ingrese la capacidad");
            etCapacidad.requestFocus();
            return;
        }

        if (pesoStr.isEmpty()) {
            etPeso.setError("Ingrese el peso");
            etPeso.requestFocus();
            return;
        }

        if (modelNumber.length() < 2) {
            etModelNumber.setError("Mínimo 2 caracteres");
            etModelNumber.requestFocus();
            return;
        }

        try {
            int capacidad = Integer.parseInt(capacidadStr);
            double peso = Double.parseDouble(pesoStr);

            if (capacidad < 1 || capacidad > 1000) {
                etCapacidad.setError("Rango: 1-1000 pasajeros");
                etCapacidad.requestFocus();
                return;
            }

            if (peso < 500 || peso > 1000000) {
                etPeso.setError("Rango: 500-1,000,000 kg");
                etPeso.requestFocus();
                return;
            }

            if (!modelosDAO.mostrarPorModelNumber(modelNumber).isEmpty()) {
                Toast.makeText(this, "❌ El modelo " + modelNumber + " ya existe", Toast.LENGTH_SHORT).show();
                etModelNumber.setError("Modelo ya registrado");
                etModelNumber.requestFocus();
                return;
            }

            Modelos nuevoModelo = new Modelos(modelNumber, capacidad, peso);
            modelosDAO.agregarModelo(nuevoModelo);

            Toast.makeText(this, "✅ Modelo " + modelNumber + " agregado exitosamente", Toast.LENGTH_SHORT).show();

            etModelNumber.setText("");
            etCapacidad.setText("");
            etPeso.setText("");
            etModelNumber.requestFocus();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ Formato numérico inválido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
