package com.nahuelpas.cuentabilidad.Database;

import android.content.Context;

import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(version = 2, entities = {Cuenta.class, Movimiento.class, Categoria.class}, exportSchema = false)
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
                                    .addMigrations(new Migration[]{ MIGRATION_1_2,
  //                                                                  MIGRATION_2_3
                                                                    })
/*                                    .addCallback(new Callback() {
                                        @Override
                                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                            super.onCreate(db);
                                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Database db = getAppDatabase(context);

                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Billetera", new Double(2275), false, false));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Santander Rio", new Double(14295.97), false, false));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Guardado CPU", new Double(0), false, false));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Prestamo Camila", new Double(2500), false, true));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Prestamo Juanca", new Double(11500), false, true));

                                                    db.CategoriaDao().add(new Categoria(db.CategoriaDao().getNextId(), "Combustible"));
                                                    db.CategoriaDao().add(new Categoria(db.CategoriaDao().getNextId(), "Facultad"));
                                                    db.CategoriaDao().add(new Categoria(db.CategoriaDao().getNextId(), "Libreria"));
                                                    db.CategoriaDao().add(new Categoria(db.CategoriaDao().getNextId(), "Joda"));
                                                    db.CategoriaDao().add(new Categoria(db.CategoriaDao().getNextId(), "Farmacia"));
                                                }
                                            });
                                        }
                                    })
*/
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE Cuenta ADD COLUMN descubierto INTEGER NOT NULL default 'false'");
            database.execSQL("ALTER TABLE GASTO ADD COLUMN anio_mes TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CATEGORIA ADD COLUMN subcategorias");
        }
    };
}
