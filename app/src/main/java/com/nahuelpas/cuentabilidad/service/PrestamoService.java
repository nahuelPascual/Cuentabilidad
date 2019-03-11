package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;

import java.util.Map;

public class PrestamoService extends MovimientoService {

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);

        Prestamo prestamo = new Prestamo();
        cargarMovimiento(elementos, prestamo);
        prestamo.setIdCuentaPrestamo(cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString()).getCodigo());
        return new Movimiento(prestamo);
    }

    @Override
    public void guardarMovimiento(Movimiento movimiento) {

    }

}
