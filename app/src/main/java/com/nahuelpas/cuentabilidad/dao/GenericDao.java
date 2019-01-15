package com.nahuelpas.cuentabilidad.dao;

import android.database.Cursor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class GenericDao<T> {

    public Long getNextId(){
        return new Long(getCantidadRegistros()+1);
    }

    @Insert
    public abstract void add(T entity);

    @Delete
    public abstract int delete (T entity);

    @Update
    public abstract int update(T entity);

    public abstract T getById(Long id);

    public abstract int getCantidadRegistros();

    public abstract List<T> getAll();
}
