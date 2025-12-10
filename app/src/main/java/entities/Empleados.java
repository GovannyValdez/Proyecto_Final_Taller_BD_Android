package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="empleados")
public class Empleados {

    @PrimaryKey
    @NonNull
    private String ssn;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;
    private String telefono;
    private double salario;
    private String numeroMembresiaSindicato;

    public Empleados() {
    }

    public Empleados(String ssn, String nombre, String apellidoPaterno, String apellidoMaterno,
                    String direccion, String telefono, double salario, String numeroMembresiaSindicato) {
        this.ssn = ssn;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.direccion = direccion;
        this.telefono = telefono;
        this.salario = salario;
        this.numeroMembresiaSindicato = numeroMembresiaSindicato;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario >= 0) {
            this.salario = salario;
        }
    }

    public String getNumeroMembresiaSindicato() {
        return numeroMembresiaSindicato;
    }

    public void setNumeroMembresiaSindicato(String numeroMembresiaSindicato) {
        this.numeroMembresiaSindicato = numeroMembresiaSindicato;
    }
}
