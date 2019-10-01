package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Cobranza;

public class CobranzaDao implements TransaccionDao<Cobranza> {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Cobranza getById (Long id) {
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.COBRANZA)){
            return movimientoMapper.mappearCobranza(movimiento);
        }
        return null;
    }

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
