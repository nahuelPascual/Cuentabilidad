package com.nahuelpas.cuentabilidad.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cuenta {

    public Cuenta(Long codigo, String descripcion, double saldo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.saldo = saldo;
    }

    @PrimaryKey
    private Long codigo;
    private String descripcion;
    private double saldo;

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
