package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Transferencia;

public class TransferenciaDao implements TransaccionDao<Transferencia> {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Transferencia getById (Long id) {
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.TRANSFERENCIA)){
            return movimientoMapper.mappearTransferencia(movimiento);
        }
        return null;
    }

    public void guardar (Transferencia transferencia) {
        movimientoDao.add(new Movimiento(transferencia));
    }

    public void eliminar (Transferencia transferencia) {
        movimientoDao.delete(new Movimiento(transferencia));
    }
}
