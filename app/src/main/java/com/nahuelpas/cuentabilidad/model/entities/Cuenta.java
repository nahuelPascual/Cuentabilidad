package com.nahuelpas.cuentabilidad.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cuenta {

    public Cuenta(Long codigo, String descripcion, double saldo, boolean prestamo) {
        this.codigo = codigo;

        this.descripcion = descripcion;
        this.saldo = saldo;
        this.prestamo = prestamo;
    }

    @PrimaryKey private Long codigo;
    private String descripcion;
    private double saldo;
    @NonNull private boolean prestamo;
    @NonNull private Moneda moneda;

    public enum Moneda {
        PESOS(0),
        DOLARES(1);

        int value;
        Moneda (int n){
            value = n;
        };
    }

    @NonNull
    public boolean isPrestamo() {
        return prestamo;
    }
    public void setPrestamo(boolean prestamo) {
        this.prestamo = prestamo;
    }

    @NonNull
    public Moneda getMoneda() {
        return moneda;
    }
    public void setMoneda(@NonNull Moneda moneda) {
        this.moneda = moneda;
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
