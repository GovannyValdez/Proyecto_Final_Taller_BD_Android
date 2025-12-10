package Controlers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Empleados;

@Dao
public interface EmpleadosDAO {

    @Insert
    void agregarEmpleado(Empleados empleado);

    @Delete
    void eliminarEmpleado(Empleados empleado);

    @Query("DELETE FROM empleados WHERE ssn = :ssn")
    void eliminarEmpleadoPorSSN(String ssn);

    @Update
    void actualizarEmpleado(Empleados empleado);

    @Query("UPDATE empleados SET nombre = :n WHERE ssn = :ssn")
    void actualizarEmpleadoPorSSN(String n, String ssn);

    @Query("SELECT * FROM empleados")
    List<Empleados> mostrarTodos();

    @Query("SELECT * FROM empleados WHERE nombre = :n")
    List<Empleados> mostrarPorNombre(String n);

    @Query("SELECT * FROM empleados WHERE ssn = :ssn LIMIT 1")
    Empleados mostrarPorSSN(String ssn);

    @Query("SELECT * FROM empleados WHERE ssn LIKE '%' || :filtro || '%' OR nombre LIKE '%' || :filtro || '%'")
    List<Empleados> buscarPorCoincidencia(String filtro);

}

