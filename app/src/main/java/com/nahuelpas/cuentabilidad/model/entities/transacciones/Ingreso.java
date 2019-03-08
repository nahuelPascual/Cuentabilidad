package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

public class Ingreso extends MovimientoBase {

    public Ingreso(){
        tipo = Movimiento.Tipo.INGRESO;
    }

}
