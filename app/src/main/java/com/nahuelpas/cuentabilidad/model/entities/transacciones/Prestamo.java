package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import java.util.Date;

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
