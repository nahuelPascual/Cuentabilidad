package com.nahuelpas.cuentabilidad.Database;

import android.app.Activity;
import android.content.Context;

import com.nahuelpas.cuentabilidad.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.Cuenta;

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

}
