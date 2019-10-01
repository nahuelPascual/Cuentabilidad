package com.nahuelpas.cuentabilidad.Model.entities.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

public class Gasto extends MovimientoBase {

    public Gasto () {
        super();
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
