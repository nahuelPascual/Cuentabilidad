package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;

import java.util.Map;

public class CompraDivisaService extends MovimientoService {

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerCuenta = (Spinner) elementos.get(SPINNER_CUENTA2);
        CompraDivisa compraDivisa = new CompraDivisa();
        cargarMovimiento(elementos, compraDivisa);
        compraDivisa.setIdCuentaDivisa(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
        return new Movimiento(compraDivisa);
    }

    @Override
    public void guardarMovimiento(Movimiento movimiento) {

    }
}
