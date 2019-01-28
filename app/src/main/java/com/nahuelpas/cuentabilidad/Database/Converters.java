package com.nahuelpas.cuentabilidad.Database;

import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date==null ? null : date.getTime();
    }

    @TypeConverter
    public Gasto.Tipo fromInt(int n) {
        if(n == Gasto.Tipo.GASTO.getValue()) return Gasto.Tipo.GASTO;
        if(n == Gasto.Tipo.INGRESO.getValue()) return Gasto.Tipo.INGRESO;
        if(n == Gasto.Tipo.PRESTAMO.getValue()) return Gasto.Tipo.PRESTAMO;
        if(n == Gasto.Tipo.PAGO.getValue()) return Gasto.Tipo.PAGO;
        return null;
    }
    @TypeConverter
    public int fromTipo(Gasto.Tipo tipo){
        return tipo.getValue();
    }

}
