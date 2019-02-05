package com.nahuelpas.cuentabilidad.model.dao;

import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class GastoDao extends GenericDao<Gasto> {

    private static final String TABLE_NAME = "Gasto";

    @Override
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE codigo = :id")
    public abstract Gasto getById(Long id);

    @Override
    @Query("SELECT codigo FROM " + TABLE_NAME + ORDER_BY_CODIGO_DESC + " LIMIT 1")
    protected abstract int getUltimoId();

    @Override
    @Query("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Override
    @Query("SELECT * FROM " + TABLE_NAME + ORDER_BY_CODIGO_DESC)
    public abstract List<Gasto> getAll();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE idCategoria = :categoria" + ORDER_BY_CODIGO_DESC)
    public abstract List<Gasto> getByCategoria(Long categoria);

}
