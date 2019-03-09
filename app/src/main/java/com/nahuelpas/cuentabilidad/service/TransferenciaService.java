package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

import java.util.Map;

public class TransferenciaService extends MovimientoService {

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerTransferencia = (Spinner) elementos.get(SPINNER_CUENTA2);
        Transferencia transferencia = new Transferencia();
        cargarMovimiento(elementos, transferencia);
        transferencia.setCuentaTransferencia(cuentaDao.getCuentaByDesc(spinnerTransferencia.getSelectedItem().toString()).getCodigo());
        return new Movimiento(transferencia);
    }
}
