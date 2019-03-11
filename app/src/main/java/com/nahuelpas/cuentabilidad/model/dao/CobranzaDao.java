package com.nahuelpas.cuentabilidad.model.dao;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;

public class CobranzaDao {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public void guardar (Cobranza cobranza) {
        movimientoDao.add(new Movimiento(cobranza));
    }

    public void eliminar (Cobranza cobranza) {
        movimientoDao.delete(new Movimiento(cobranza));
    }

    public void update (Cobranza cobranza) {
        movimientoDao.update(new Movimiento(cobranza));
    }

}
