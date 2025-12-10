package entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="modelos")

public class Modelos{


    @PrimaryKey
    @NonNull
    private String modelNumber;
    private int capacidad;
    private double peso;

    public Modelos() {
    }

    public Modelos(String modelNumber, int capacidad, double peso) {
        this.modelNumber = modelNumber;
        this.capacidad = capacidad;
        this.peso = peso;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad > 0 && capacidad <= 1000) {
            this.capacidad = capacidad;
        }
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso >= 500 && peso > 0 && peso <= 1_000_000) {
            this.peso = peso;
        }
    }
}

