package com.nahuelpas.cuentabilidad.dao;

import android.database.Cursor;

import com.google.android.material.tabs.TabLayout;
import com.nahuelpas.cuentabilidad.model.Cuenta;

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
    @Query("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Override
    @Query("SELECT * FROM " + TABLE_NAME)
    public abstract List<Cuenta> getAll();
}
