package com.nahuelpas.cuentabilidad.model.entities;

import com.nahuelpas.cuentabilidad.Database.Converters;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class Cuenta {

    public Cuenta(Long codigo, String descripcion, double saldo, boolean prestamo, Moneda moneda) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.prestamo = prestamo;
        this.moneda = moneda;
    }

    @PrimaryKey private Long codigo;
    private String descripcion;
    private double saldo;
    @NonNull private boolean prestamo;
    private Moneda moneda;

    public enum Moneda {
        PESOS(0),
        DOLARES(1);

        int value;
        Moneda (int n){
            value = n;
        };
        public int getValue(){
            return value;
        }
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
