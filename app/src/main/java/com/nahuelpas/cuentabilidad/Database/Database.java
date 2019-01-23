package com.nahuelpas.cuentabilidad.Database;

import android.content.Context;

import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(version = 1, entities = {Cuenta.class, Gasto.class, Categoria.class}, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Gasto ADD cuenta NUMBER");
        }
    };

    abstract public CuentaDao CuentaDao();
    abstract public CategoriaDao CategoriaDao();
    abstract public GastoDao GastoDao();

    public static Database getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "user-database")
                                    .allowMainThreadQueries()
                                    .fallbackToDestructiveMigration()
//                                    .addMigrations(MIGRATION_1_2)
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
