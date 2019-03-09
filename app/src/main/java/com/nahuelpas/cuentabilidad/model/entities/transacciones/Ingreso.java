package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import java.util.Date;

public class Ingreso extends MovimientoBase {

    public Ingreso(){
        super();
        tipo = Movimiento.Tipo.INGRESO;
    }

}
