package com.nahuelpas.cuentabilidad.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.model.dto.GastoDto;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class GastoService /*extends Service*/ {

//    CategoriaDao categoriaDao ;

    public GastoDto map (Gasto gasto){
        GastoDto gastoDto = new GastoDto();
        //gastoDto.setFecha(DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()).toString());
        gastoDto.setDescripcion(gasto.getDescripcion());
       // gastoDto.setValor(String.format("%.2f",gasto.getMonto()));
        return gastoDto; // TODO no usar un Dto, sino directamente la entity necesito el ID y la categoría para el detalle y aplicar filtros
    }

    public List<GastoDto> map (List<Gasto> gastos){
        List<GastoDto> gastosDto = new ArrayList<>();
        for (Gasto gasto : gastos) {
            gastosDto.add(map(gasto));
        }
        return gastosDto;
    }

    public boolean tieneDecimales (double montoGasto) {
        return montoGasto != (int) montoGasto;
    }
/*
    public Categoria getCategoria (Gasto gasto) {
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        return categoriaDao.getById(gasto.getIdCategoria());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
*/
}
