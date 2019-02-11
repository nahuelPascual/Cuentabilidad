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
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.NuevoGastoActivity;
import com.nahuelpas.cuentabilidad.exception.BusinessException;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

public class GastoService extends MovimientoService {

    private CuentaService cuentaService = new CuentaService();

    public final static String PARAM_ID_GASTO = "idGasto";
    public final static String PARAM_TIPO_GASTO = "tipoGasto";

    public void actualizarSaldo(double montoGasto, Cuenta cuenta, CuentaDao dao) throws BusinessException {
        double nuevoSaldo = cuenta.getSaldo()-montoGasto;
        if(nuevoSaldo<=0) throw new BusinessException("Saldo insuficiente en la cuenta.");
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

    public void guardarGasto(Gasto gasto) {
        //TODO no se usa
       /* try {
            cuentaService.actualizarSaldo(gasto.getMonto(), cuentaDao.getById(gasto.getIdCuenta()));
            gastoDao.add(gasto);
        } catch (ValidationException e) {
            Toast.makeText(MainActivity.APP_CONTEXT, e.getMessage(), Toast.LENGTH_LONG).show();
        }*/
    }

    public void eliminarGasto(Gasto gasto){
        Cuenta cuenta = cuentaDao.getById(gasto.getIdCuenta());
        cuentaService.actualizarSaldoIngreso(gasto.getMonto(), cuentaDao.getById(gasto.getIdCuenta()));
        gastoDao.delete(gasto);
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
