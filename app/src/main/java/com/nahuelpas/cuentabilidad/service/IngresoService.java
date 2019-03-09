package com.nahuelpas.cuentabilidad.service;

import android.widget.EditText;
import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;

import java.util.Map;

public class IngresoService extends MovimientoService {

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Ingreso ingreso = new Ingreso();
        cargarMovimiento(elementos, ingreso);
        return new Movimiento(ingreso);
    }

}
