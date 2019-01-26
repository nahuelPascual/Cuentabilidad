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

@androidx.room.Database(version = 2, entities = {Cuenta.class, Gasto.class, Categoria.class}, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

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
//                                    .addMigrations(new Migration[]{ MIGRATION_1_2
//                                                                    })
/*                                    .addCallback(new Callback() {
                                        @Override
                                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                            super.onCreate(db);
                                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Database db = getAppDatabase(context);

                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Billetera", new Double(11500), false));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Santander Rio", new Double(15000), false));
                                                    db.CuentaDao().add(new Cuenta(db.CuentaDao().getNextId(), "Guardado CPU", new Double(11500), false));

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
            database.execSQL("ALTER TABLE Cuenta ADD COLUMN descubierto INTEGER NOT NULL default 'false'");
        }
    };
}
