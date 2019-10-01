package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Ingreso;

public class IngresoDao implements TransaccionDao<Ingreso> {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Ingreso getById (Long id){
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
