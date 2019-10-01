package com.nahuelpas.cuentabilidad.Database;

import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.CompraDivisa;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Transferencia;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {

    MovimientoMapper movimientoMapper = new MovimientoMapper();

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date==null ? null : date.getTime();
    }

    @TypeConverter
    public Movimiento.Tipo fromIntToTipo(int n) {
        if(n == Movimiento.Tipo.GASTO.getValue()) return Movimiento.Tipo.GASTO;
        if(n == Movimiento.Tipo.INGRESO.getValue()) return Movimiento.Tipo.INGRESO;
        if(n == Movimiento.Tipo.PRESTAMO.getValue()) return Movimiento.Tipo.PRESTAMO;
        if(n == Movimiento.Tipo.COBRANZA.getValue()) return Movimiento.Tipo.COBRANZA;
        if(n == Movimiento.Tipo.TRANSFERENCIA.getValue()) return Movimiento.Tipo.TRANSFERENCIA;
        if(n == Movimiento.Tipo.COMPRA_DIVISA.getValue()) return Movimiento.Tipo.COMPRA_DIVISA;
        return null;
    }
    @TypeConverter
    public int fromTipoToInt(Movimiento.Tipo tipo){
        return tipo.getValue();
    }

    @TypeConverter
    public Cuenta.Moneda fromIntToMoneda(int n) {
        if(n == Cuenta.Moneda.PESOS.getValue()) return Cuenta.Moneda.PESOS;
        if(n == Cuenta.Moneda.DOLARES.getValue()) return Cuenta.Moneda.DOLARES;
        return null;
    }
    @TypeConverter
    public int fromMonedaoToInt(Cuenta.Moneda moneda){
        return moneda.getValue();
    }

    @TypeConverter
    public Gasto movToGasto (Movimiento movimiento) {
        return movimientoMapper.mappearGasto(movimiento);
    }
    @TypeConverter
    public Ingreso movToIngreso (Movimiento movimiento) {
        return movimientoMapper.mappearIngreso(movimiento);
    }
    @TypeConverter
    public Prestamo movToPrestamo (Movimiento movimiento) {
        return movimientoMapper.mappearPrestamo(movimiento);
    }
//    @TypeConverter
//    public Cobranza movToCobranza (Movimiento movimiento) {
//        return movimientoMapper.mappearCobranza(movimiento);
//    }
    @TypeConverter
    public CompraDivisa movToCompraDivisa (Movimiento movimiento) {
        return movimientoMapper.mappearCompraDivisa(movimiento);
    }
    @TypeConverter
    public Transferencia movToTransferencia (Movimiento movimiento) {
        return movimientoMapper.mappearTransferencia(movimiento);
    }
}
