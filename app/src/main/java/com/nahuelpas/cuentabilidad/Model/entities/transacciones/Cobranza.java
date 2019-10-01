package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

public class Cobranza extends MovimientoBase {

    public Cobranza () {
        super();
        tipo = Movimiento.Tipo.COBRANZA;
    }

    Long idCuentaPrestamo;

    public Long getIdCuentaPrestamo() {
        return idCuentaPrestamo;
    }

    public void setIdCuentaPrestamo(Long idCuentaPrestamo) {
        this.idCuentaPrestamo = idCuentaPrestamo;
    }
}
