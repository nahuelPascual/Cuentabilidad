package com.nahuelpas.cuentabilidad.Model.dao;

import com.nahuelpas.cuentabilidad.Model.entities.Categoria;

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
    @Query("SELECT codigo FROM " + TABLE_NAME + ORDER_BY_CODIGO_DESC + " LIMIT 1")
    protected abstract int getUltimoId();

    @Override
    @Query("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Query("SELECT * FROM " + TABLE_NAME)
    public abstract List<Categoria> getAll();

    @Query("SELECT descripcion FROM " + TABLE_NAME + ORDER_BY_DESCRIPCION)
    public abstract List<String> getDescripciones();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE descripcion = :descr")
    public abstract Categoria getCategoriaByDesc(String descr);
}
