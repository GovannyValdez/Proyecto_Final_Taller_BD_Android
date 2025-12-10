package com.example.proyecto_final_taller_bd_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Aeropuerto_BD.Conexion_BD;
import Controlers.EmpleadosDAO;
import entities.Empleados;

public class ModificarEmpleadoActivity extends AppCompatActivity {

    EditText txtSSN_Buscar, txtNombre, txtAP, txtAM, txtDireccion, txtTelefono, txtSalario, txtMembresia;
    Button btnBuscar, btnModificar;

    EmpleadosDAO empleadosDAO;
    Empleados empleadoActual = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        empleadosDAO = Conexion_BD.getAppDatabase(this).empleadosDAO();

        txtSSN_Buscar = findViewById(R.id.txtSSN_Buscar);
        txtNombre = findViewById(R.id.txtNombre);
        txtAP = findViewById(R.id.txtAP);
        txtAM = findViewById(R.id.txtAM);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtSalario = findViewById(R.id.txtSalario);
        txtMembresia = findViewById(R.id.txtMembresia);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnModificar = findViewById(R.id.btnModificar);


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ssn = txtSSN_Buscar.getText().toString().trim();

                if (ssn.isEmpty()) {
                    Toast.makeText(ModificarEmpleadoActivity.this, "Ingrese un SSN", Toast.LENGTH_SHORT).show();
                    return;
                }

                Empleados empleado = empleadosDAO.mostrarPorSSN(ssn);

                if (empleado == null) {
                    Toast.makeText(ModificarEmpleadoActivity.this, "No existe un empleado con ese SSN", Toast.LENGTH_SHORT).show();
                    return;
                }

                empleadoActual = empleado;

                txtNombre.setText(empleadoActual.getNombre());
                txtAP.setText(empleadoActual.getApellidoPaterno());
                txtAM.setText(empleadoActual.getApellidoMaterno());
                txtDireccion.setText(empleadoActual.getDireccion());
                txtTelefono.setText(empleadoActual.getTelefono());
                txtSalario.setText(String.valueOf(empleadoActual.getSalario()));
                txtMembresia.setText(empleadoActual.getNumeroMembresiaSindicato());

                Toast.makeText(ModificarEmpleadoActivity.this, "Empleado encontrado", Toast.LENGTH_SHORT).show();
            }
        });


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (empleadoActual == null) {
                    Toast.makeText(ModificarEmpleadoActivity.this, "Primero busque un empleado", Toast.LENGTH_SHORT).show();
                    return;
                }

                empleadoActual.setNombre(txtNombre.getText().toString().trim());
                empleadoActual.setApellidoPaterno(txtAP.getText().toString().trim());
                empleadoActual.setApellidoMaterno(txtAM.getText().toString().trim());
                empleadoActual.setDireccion(txtDireccion.getText().toString().trim());
                empleadoActual.setTelefono(txtTelefono.getText().toString().trim());
                empleadoActual.setNumeroMembresiaSindicato(txtMembresia.getText().toString().trim());

                try {
                    double salario = Double.parseDouble(txtSalario.getText().toString().trim());
                    empleadoActual.setSalario(salario);
                } catch (Exception e) {
                    Toast.makeText(ModificarEmpleadoActivity.this, "Salario inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                empleadosDAO.actualizarEmpleado(empleadoActual);

                Toast.makeText(ModificarEmpleadoActivity.this, "Empleado actualizado correctamente", Toast.LENGTH_LONG).show();
            }
        });
    }
}
