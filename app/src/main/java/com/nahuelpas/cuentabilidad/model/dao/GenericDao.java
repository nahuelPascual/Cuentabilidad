package com.nahuelpas.cuentabilidad.model.dao;

import android.database.Cursor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

@Dao
public abstract class GenericDao<T> {

    protected final String ORDER_BY_CODIGO_DESC = " ORDER BY codigo DESC";
    protected final String ORDER_BY_DESCRIPCION = " ORDER BY descripcion";
    protected final int FALSE = 0;
    protected final int TRUE = 1;

    public Long getNextId(){
        return new Long(getUltimoId()+1);
    }

    @RawQuery
    public abstract int ejecutarQuery(SupportSQLiteQuery query);

    @Insert
    public abstract void add(T entity);

    @Delete
    public abstract int delete (T entity);

    @Update
    public abstract int update(T entity);

    public abstract T getById(Long id);

    protected abstract int getUltimoId();

    public abstract int getCantidadRegistros();

    public abstract List<T> getAll();
}
