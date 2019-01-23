package com.nahuelpas.cuentabilidad.model.dto;

import java.text.DateFormat;
import java.util.Date;

public class GastoDto {

    Date fecha;
    String descripcion;
    double valor;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
