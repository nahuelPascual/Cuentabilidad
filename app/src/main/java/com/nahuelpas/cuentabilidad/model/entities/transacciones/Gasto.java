package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

public class Gasto extends MovimientoBase {

    public Gasto () {
        tipo = Movimiento.Tipo.GASTO;
    }

    Long idCategoria;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
