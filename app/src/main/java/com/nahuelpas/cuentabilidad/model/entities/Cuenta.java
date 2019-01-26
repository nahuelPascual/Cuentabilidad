package com.nahuelpas.cuentabilidad.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cuenta {

    public Cuenta(Long codigo, String descripcion, double saldo, boolean descubierto) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.descubierto = descubierto;
    }

    @PrimaryKey private Long codigo;
    private String descripcion;
    private double saldo;
    @NonNull private boolean descubierto ;

    public boolean isDescubierto() {
        return descubierto;
    }

    public void setDescubierto(boolean descubierto) {
        this.descubierto = descubierto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }
}
