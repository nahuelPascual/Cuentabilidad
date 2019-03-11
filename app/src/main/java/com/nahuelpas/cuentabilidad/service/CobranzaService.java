package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.MovimientoBase;

import java.util.Map;

public class CobranzaService extends MovimientoService {

    @Override
    public void guardarMovimiento(Movimiento movimiento) {

    }

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);
        Cobranza cobranza = new Cobranza();
        cargarMovimiento(elementos, cobranza);
        cobranza.setIdCuentaPrestamo(cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString()).getCodigo());
        return new Movimiento(cobranza);
    }
}
