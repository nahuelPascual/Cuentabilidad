package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Prestamo;

public class PrestamoDao implements TransaccionDao<Prestamo> {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Prestamo getById (Long id) {
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
