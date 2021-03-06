package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

public class Transferencia extends MovimientoBase {

    public Transferencia () {
        super();
        tipo = Movimiento.Tipo.TRANSFERENCIA;
    }

    Long idCuentaTransferencia;

    public Long getCuentaTransferencia() {
        return idCuentaTransferencia;
    }

    public void setCuentaTransferencia(Long idCuentaTransferencia) {
        this.idCuentaTransferencia = idCuentaTransferencia;
    }
}
