package Aeropuerto_BD;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Controlers.EmpleadosDAO;
import Controlers.ModelosDAO;
import entities.Empleados;
import entities.Modelos;

@Database(entities = {Empleados.class, Modelos.class}, version = 1)
public abstract class Conexion_BD extends RoomDatabase {




    private static Conexion_BD INSTANCE;

    public abstract EmpleadosDAO empleadosDAO();
    public abstract ModelosDAO modelosDAO();


    public static Conexion_BD getAppDatabase(Context context){

        if(INSTANCE==null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Conexion_BD.class, "BD_Conexion").build();

        }

        return INSTANCE;

    }

    public static void desstroyInstance(){INSTANCE=null;}

}

