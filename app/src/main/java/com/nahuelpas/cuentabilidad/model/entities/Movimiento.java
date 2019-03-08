package com.nahuelpas.cuentabilidad.model.entities;

import com.nahuelpas.cuentabilidad.Database.Converters;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class Movimiento {

    public Movimiento (@NonNull Gasto mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
        idCategoria = mov.getIdCategoria();
    }
    public Movimiento (@NonNull Ingreso mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
    }
    public Movimiento (@NonNull Prestamo mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
        idCuentaAlt = mov.getIdCuentaPrestamo();
    }
    public Movimiento (@NonNull Cobranza mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
        idCuentaAlt = mov.getIdCuentaPrestamo();
    }
    public Movimiento (@NonNull CompraDivisa mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
        idCuentaAlt = mov.getIdCuentaDivisa();
    }
    public Movimiento (@NonNull Transferencia mov) {
        codigo = mov.getCodigo();
        fecha = mov.getFecha();
        descripcion = mov.getDescripcion();
        idCuenta = mov.getIdCuenta();
        monto = mov.getMonto();
        tipo = mov.getTipo();
        idCuentaAlt = mov.getCuentaTransferencia();
    }

    @PrimaryKey
    private Long codigo;
    private Date fecha;
    private String descripcion;
    @ForeignKey(entity = Cuenta.class, parentColumns = {"codigo"}, childColumns = {"idCuenta"})
    private Long idCuenta;
    @ForeignKey(entity = Categoria.class, parentColumns = {"codigo"}, childColumns = {"idCategoria"})
    @Nullable private Long idCategoria;
    private double monto;
    private Tipo tipo;
    private String anio_mes;
    @ForeignKey(entity = Cuenta.class, parentColumns = {"codigo"}, childColumns = {"idCuentaAlt"})
    @Nullable private Long idCuentaAlt;

    public enum Tipo {
        GASTO(0),
        INGRESO(1),
        PRESTAMO(2),
        COBRANZA(3),
        TRANSFERENCIA(4),
        COMPRA_DIVISA(5);

        Tipo(int n){
            value = n;
        }
        private int value;
        public int getValue(){
            return value;
        }
        static public List<Integer> getAllValues(){
            List<Integer> lista = new ArrayList<>();
            for(Tipo tipo : values()){
                lista.add(tipo.getValue());
            }
            return lista;
        }
    }

    public String getAnio_mes() {
        return anio_mes;
    }
    public void setAnio_mes(String anio_mes) {
        this.anio_mes = anio_mes;
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

    @Nullable
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

    @Nullable
    public Long getIdCuentaAlt() {
        return idCuentaAlt;
    }
    public void setIdCuentaAlt(@Nullable Long idCuentaAlt) {
        this.idCuentaAlt = idCuentaAlt;
    }
}
