package Aeropuerto_BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Controlers.EmpleadosDAO;
import Controlers.ModelosDAO;
import entities.Empleados;
import entities.Modelos;

@Database(entities = {Empleados.class, Modelos.class}, version = 1, exportSchema = false)
public abstract class Conexion_BD extends RoomDatabase {

    private static volatile Conexion_BD INSTANCE;

    public abstract EmpleadosDAO empleadosDAO();
    public abstract ModelosDAO modelosDAO();

    public static Conexion_BD getAppDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (Conexion_BD.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    Conexion_BD.class,
                                    "Conexion_BD"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
