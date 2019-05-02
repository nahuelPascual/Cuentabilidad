package com.nahuelpas.cuentabilidad.model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;

public class IngresoDao{

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Ingreso getById (long id){
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.INGRESO)){
            return movimientoMapper.mappearIngreso(movimiento);
        }
        return null;
    }

    public void guardar(Ingreso ingreso) {
        movimientoDao.add(new Movimiento(ingreso));
    }

    public void eliminar(Ingreso ingreso) {
        movimientoDao.delete(new Movimiento(ingreso));
    }
}
