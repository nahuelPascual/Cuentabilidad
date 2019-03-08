package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

public class CompraDivisa extends MovimientoBase {

    public CompraDivisa () {
        tipo = Movimiento.Tipo.COMPRA_DIVISA;
    }

    Long idCuentaDivisa;

    public Long getIdCuentaDivisa() {
        return idCuentaDivisa;
    }

    public void setIdCuentaDivisa(Long idCuentaDivisa) {
        this.idCuentaDivisa = idCuentaDivisa;
    }
}
