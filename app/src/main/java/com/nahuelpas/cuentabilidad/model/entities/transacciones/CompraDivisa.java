package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

public class CompraDivisa extends MovimientoBase {

    public CompraDivisa () {
        super();
        tipo = Movimiento.Tipo.COMPRA_DIVISA;
    }

    Long idCuentaDivisa;
    double montoDivisa;

    public Long getIdCuentaDivisa() {
        return idCuentaDivisa;
    }

    public void setIdCuentaDivisa(Long idCuentaDivisa) {
        this.idCuentaDivisa = idCuentaDivisa;
    }

    public double getMontoDivisa() {
        return montoDivisa;
    }

    public void setMontoDivisa(double montoDivisa) {
        this.montoDivisa = montoDivisa;
    }
}
