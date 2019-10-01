package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

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
