package com.nahuelpas.cuentabilidad.model.dao;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;

public class CompraDivisaDao {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public void guardar (CompraDivisa compraDivisa) {
        movimientoDao.add(new Movimiento(compraDivisa));
    }

    public void eliminar (CompraDivisa compraDivisa) {
        movimientoDao.delete(new Movimiento(compraDivisa));
    }
}
