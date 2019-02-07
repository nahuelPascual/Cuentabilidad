package com.nahuelpas.cuentabilidad.model.entities;

import com.nahuelpas.cuentabilidad.Database.Converters;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class Gasto {

    @PrimaryKey
    private Long codigo;
    private Date fecha;
    private String descripcion;
    @ForeignKey(entity = Categoria.class, parentColumns = {"codigo"}, childColumns = {"idCategoria"})
    private Long idCategoria;
    @ForeignKey(entity = Cuenta.class, parentColumns = {"codigo"}, childColumns = {"idCuenta"})
    private Long idCuenta;
    private double monto;
    private Tipo tipo;

    public enum Tipo {
        GASTO(0),
        INGRESO(1),
        PRESTAMO(2),
        PAGO(3);

        Tipo(int n){
            value = n;
        }
        private int value;
        public int getValue(){
            return value;
        }
    }

    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(Long cuenta) {
        this.idCuenta = cuenta;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long tipoGasto) {
        this.idCategoria = tipoGasto;
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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
}
