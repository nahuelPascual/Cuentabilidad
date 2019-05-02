package com.nahuelpas.cuentabilidad.model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;

public class PrestamoDao {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Prestamo getById (long id) {
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.PRESTAMO)){
            return movimientoMapper.mappearPrestamo(movimiento);
        }
        return null;
    }

    public void guardar (Prestamo prestamo) {
        movimientoDao.add(new Movimiento(prestamo));
    }

    public void eliminar (Prestamo prestamo) {
        movimientoDao.delete(new Movimiento(prestamo));
    }
}