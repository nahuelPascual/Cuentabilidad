package com.nahuelpas.cuentabilidad.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.AbsSpinner;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.exception.BusinessException;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.GenericDao;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.model.dto.GastoDto;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class GastoService /*extends Service*/ {

//    CategoriaDao categoriaDao ;

    public final static String PARAM_ID_GASTO = "idGasto";

    public void actualizarSaldo(double montoGasto, Cuenta cuenta, CuentaDao dao) throws BusinessException {
        double nuevoSaldo = cuenta.getSaldo()-montoGasto;
        if(nuevoSaldo<=0) throw new BusinessException();
        cuenta.setSaldo(nuevoSaldo);
        dao.update(cuenta); // TODO ver cómo generar un dao acá
    }

    public int getPosicionItemSpinner(Spinner spinner, String descripcion) {
        for (int i=0; i<spinner.getCount(); i++){
            String item = (String) spinner.getItemAtPosition(i);
            if(item.equals(descripcion)){
                return i;
            }
        }
        return -1;
    }

    public String formatearGasto(double gasto) {
        return !tieneDecimales(gasto) ?
                String.format("%.0f",gasto) : String.format("%.2f",gasto);
    }

    private boolean tieneDecimales (double montoGasto) {
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
