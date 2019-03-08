package com.nahuelpas.cuentabilidad.model.entities.transacciones;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import java.util.Date;


public abstract class MovimientoBase {

    protected Long codigo;
    protected Date fecha;
    protected String descripcion;
    protected Long idCuenta;
    protected double monto;
    protected Movimiento.Tipo tipo;
    protected String anio_mes;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

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

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Movimiento.Tipo getTipo() {
        return tipo;
    }

    public String getAnio_mes() {
        return anio_mes;
    }

    public void setAnio_mes(String anio_mes) {
        this.anio_mes = anio_mes;
    }
}
