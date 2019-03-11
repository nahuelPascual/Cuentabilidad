package com.nahuelpas.cuentabilidad.model.dao;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

public class TransferenciaDao {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public void guardar (Transferencia transferencia) {
        movimientoDao.add(new Movimiento(transferencia));
    }

    public void eliminar (Transferencia transferencia) {
        movimientoDao.delete(new Movimiento(transferencia));
    }
}
