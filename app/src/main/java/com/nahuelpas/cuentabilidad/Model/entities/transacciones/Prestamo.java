package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

public class Prestamo extends MovimientoBase {

    public Prestamo(){
        super();
        tipo = Movimiento.Tipo.PRESTAMO;
    }

    Long idCuentaPrestamo;

    public Long getIdCuentaPrestamo() {
        return idCuentaPrestamo;
    }

    public void setIdCuentaPrestamo(Long idCuentaPrestamo) {
        this.idCuentaPrestamo = idCuentaPrestamo;
    }
}
