package com.nahuelpas.cuentabilidad.dao;

import android.database.Cursor;

import com.nahuelpas.cuentabilidad.model.Categoria;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class CategoriaDao extends GenericDao<Categoria> {

    private static final String TABLE_NAME = "Categoria";

    @Override
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE codigo = :id")
    public abstract Categoria getById(Long id);

    @Override
    @Query("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Override
    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY descripcion")
    public abstract List<Categoria> getAll();

    @Query("SELECT descripcion FROM " + TABLE_NAME)
    public abstract List<String> getCategoriasForDropdown();

    @Query("SELECT descripcion AS _id FROM " + TABLE_NAME)
    public abstract Cursor getCategoriasCursor();
}
