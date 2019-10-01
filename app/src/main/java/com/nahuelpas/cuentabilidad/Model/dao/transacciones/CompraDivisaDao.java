package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.CompraDivisa;

public class CompraDivisaDao implements TransaccionDao<CompraDivisa>  {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public CompraDivisa getById(Long id) {
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.COMPRA_DIVISA)){
            return movimientoMapper.mappearCompraDivisa(movimiento);
        }
        return null;
    }

    public void guardar (CompraDivisa compraDivisa) {
        movimientoDao.add(new Movimiento(compraDivisa));
    }

    public void eliminar (CompraDivisa compraDivisa) {
        movimientoDao.delete(new Movimiento(compraDivisa));
    }

    public void update (CompraDivisa compraDivisa){
        movimientoDao.update(new Movimiento(compraDivisa));
    }
}
