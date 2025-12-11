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

import Aeropuerto_BD.Conexion_BD;
import Controlers.ModelosDAO;
import entities.Modelos;

import java.util.ArrayList;
import java.util.List;

public class ConsultarModeloActivity extends AppCompatActivity {

    private TextInputEditText etBuscar;
    private Button btnBuscar, btnMostrarTodos, btnCancelar;
    private ListView lvModelos;
    private TextView tvContador, tvSinResultados;
    private ModelosDAO modelosDAO;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaModelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_modelo);

        Conexion_BD db = Conexion_BD.getAppDatabase(this);
        modelosDAO = db.modelosDAO();

        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnMostrarTodos = findViewById(R.id.btnMostrarTodos);
        btnCancelar = findViewById(R.id.btnCancelar);
        lvModelos = findViewById(R.id.lvModelos);
        tvContador = findViewById(R.id.tvContador);
        tvSinResultados = findViewById(R.id.tvSinResultados);

        listaModelos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listaModelos);
        lvModelos.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> buscarModelos());
        btnMostrarTodos.setOnClickListener(v -> mostrarTodosModelos());
        btnCancelar.setOnClickListener(v -> finish());

        etBuscar.setOnEditorActionListener((v, actionId, event) -> {
            buscarModelos();
            return true;
        });

        tvSinResultados.setVisibility(View.VISIBLE);
        lvModelos.setVisibility(View.GONE);
        actualizarContador(0);
    }

    private void buscarModelos() {
        String filtro = etBuscar.getText().toString().trim();

        if (filtro.isEmpty()) {
            Toast.makeText(this, "Ingrese un término de búsqueda", Toast.LENGTH_SHORT).show();
            etBuscar.requestFocus();
            return;
        }

        List<Modelos> resultados = modelosDAO.buscarPorCoincidencia(filtro);
        mostrarResultados(resultados);
    }

    private void mostrarTodosModelos() {
        List<Modelos> todosModelos = modelosDAO.mostrarTodos();
        mostrarResultados(todosModelos);
    }

    private void mostrarResultados(List<Modelos> modelos) {
        listaModelos.clear();

        if (modelos.isEmpty()) {
            tvSinResultados.setVisibility(View.VISIBLE);
            lvModelos.setVisibility(View.GONE);
            actualizarContador(0);
            Toast.makeText(this, "No se encontraron modelos", Toast.LENGTH_SHORT).show();
        } else {
            for (Modelos modelo : modelos) {
                String item = String.format("%s\nCapacidad: %d pasajeros | Peso: %.2f kg",
                        modelo.getModelNumber(),
                        modelo.getCapacidad(),
                        modelo.getPeso());
                listaModelos.add(item);
            }

            adapter.notifyDataSetChanged();
            tvSinResultados.setVisibility(View.GONE);
            lvModelos.setVisibility(View.VISIBLE);
            actualizarContador(modelos.size());
        }
    }

    private void actualizarContador(int cantidad) {
        String texto = cantidad + " modelo" + (cantidad != 1 ? "s" : "") + " encontrado" + (cantidad != 1 ? "s" : "");
        tvContador.setText(texto);
    }
}