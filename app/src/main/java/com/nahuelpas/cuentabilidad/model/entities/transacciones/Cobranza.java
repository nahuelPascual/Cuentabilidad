package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

public class Cobranza extends MovimientoBase {

    public Cobranza () {
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
