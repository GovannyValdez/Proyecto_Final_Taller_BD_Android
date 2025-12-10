package Controlers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Modelos;

@Dao
public interface ModelosDAO {


    @Insert
    void agregarModelo(Modelos modelo);


    @Delete
    void eliminarModelo(Modelos modelo);

    @Query("DELETE FROM modelos WHERE modelNumber = :mn")
    void eliminarModeloPorModelNumber(String mn);


    @Update
    void actualizarModelo(Modelos modelo);

    @Query("UPDATE modelos SET capacidad = :cap, peso = :peso WHERE modelNumber = :mn")
    void actualizarModeloPorModelNumber(int cap, double peso, String mn);


    @Query("SELECT * FROM modelos")
    List<Modelos> mostrarTodos();

    @Query("SELECT * FROM modelos WHERE modelNumber = :mn")
    List<Modelos> mostrarPorModelNumber(String mn);

    @Query("SELECT * FROM modelos WHERE modelNumber LIKE :pattern")
    List<Modelos> buscarPorModelNumberSimilar(String pattern);

    @Query("SELECT * FROM modelos WHERE modelNumber LIKE :mn || '%'")
    List<Modelos> mostrarPorCoincidenciaInicio(String mn);

    @Query("SELECT * FROM modelos WHERE modelNumber LIKE '%' || :filtro || '%'")
    List<Modelos> buscarPorCoincidencia(String filtro);

}

