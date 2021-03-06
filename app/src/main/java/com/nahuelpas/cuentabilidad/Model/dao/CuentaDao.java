package com.nahuelpas.cuentabilidad.Model.dao;

import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class CuentaDao extends GenericDao<Cuenta> {

    private final String TABLE_NAME = "Cuenta";

    @Override
    @Query("SELECT * FROM " + TABLE_NAME + " WHERE codigo = :id")
    public abstract Cuenta getById(Long id);

    @Override
    @Query("SELECT codigo FROM " + TABLE_NAME + ORDER_BY_CODIGO_DESC + " LIMIT 1")
    protected abstract int getUltimoId();

    @Override
    @Query("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Query("SELECT * FROM " + TABLE_NAME)
    public abstract List<Cuenta> getAll();

    @Query ("SELECT descripcion FROM " + TABLE_NAME +
            " WHERE prestamo = " + FALSE +
            " AND moneda = :moneda"
            + ORDER_BY_DESCRIPCION)
    public abstract List<String> getDescripciones(int moneda);

    @Query("SELECT descripcion FROM " + TABLE_NAME + " WHERE prestamo = " + TRUE + ORDER_BY_DESCRIPCION)
    public abstract List<String> getDescripcionesPrestamo();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE descripcion = :descr")
    public abstract Cuenta getCuentaByDesc(String descr);
}
