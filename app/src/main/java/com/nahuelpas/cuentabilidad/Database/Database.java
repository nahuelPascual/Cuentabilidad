package com.nahuelpas.cuentabilidad.Database;

import android.content.Context;

import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(version = 1, entities = {Cuenta.class, Movimiento.class, Categoria.class}, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    abstract public CuentaDao CuentaDao();
    abstract public CategoriaDao CategoriaDao();
    abstract public MovimientoDao GastoDao();

    public static Database getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "user-database")
                                    .allowMainThreadQueries()
                                    .fallbackToDestructiveMigration()
//                                    .addMigrations(new Migration[]{ MIGRATION_1_2,
//                                                                    })
/*                                    .addCallback(new Callback() {
                                        @Override
                                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                            super.onCreate(db);
                                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Database db = getAppDatabase(context);


                                                }
                                            });
                                        }
                                    })
*/
                                    .build();
                }
            }
            prePopulate();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    private static void prePopulate(){
        CuentaDao cuentaDao = new CuentaDao_Impl(INSTANCE);
        if(cuentaDao.getAll().size() == 0){
            INSTANCE.clearAllTables();

            cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "Billetera", new Double(330), false, Cuenta.Moneda.PESOS));
            cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "Santander Rio", new Double(1517.60), false, Cuenta.Moneda.PESOS));
            cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "Guardado CPU", new Double(0), false, Cuenta.Moneda.PESOS));
//        cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "Prestamo Camila", new Double(2500), false, Cuenta.Moneda.PESOS));
            cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "Prestamo Juanca", new Double(5000), true, Cuenta.Moneda.PESOS));
            cuentaDao.add(new Cuenta(cuentaDao.getNextId(), "USD", new Double(7141), false, Cuenta.Moneda.DOLARES));

            CategoriaDao categoriaDao = new CategoriaDao_Impl(INSTANCE);
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Nafta"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "GNC"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Mecánico"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Apuntes"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Libreria"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Joda"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Farmacia"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Comida"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Regalos"));
            categoriaDao.add(new Categoria(categoriaDao.getNextId(), "Servicios"));
        }
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE Cuenta ADD COLUMN descubierto INTEGER NOT NULL default 'false'");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CATEGORIA ADD COLUMN subcategorias");
        }
    };
}
