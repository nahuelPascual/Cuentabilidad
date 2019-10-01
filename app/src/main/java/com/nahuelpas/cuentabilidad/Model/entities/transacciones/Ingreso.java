package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

public class Ingreso extends MovimientoBase {

    public Ingreso(){
        super();
        tipo = Movimiento.Tipo.INGRESO;
    }

}
