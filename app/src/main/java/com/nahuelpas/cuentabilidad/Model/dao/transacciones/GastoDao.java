package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Categoria;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Gasto;

import java.util.List;

public class GastoDao implements TransaccionDao<Gasto>  {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public Gasto getById(Long id) {
        Movimiento movimiento = movimientoDao.getById(id);
        if(movimiento.getTipo().equals(Movimiento.Tipo.GASTO)){
            return movimientoMapper.mappearGasto(movimiento);
        }
        return null;
    }

    public List<Gasto> getAll() {
        return movimientoMapper.mappearGasto(movimientoDao.getAll(Movimiento.Tipo.GASTO.getValue()));
    }

    public List<Gasto> getByCategoria(Categoria categoria) {
        return movimientoMapper.mappearGasto(movimientoDao.getByCategoria(categoria.getCodigo(), Movimiento.Tipo.GASTO.getValue()));
    }

    public double getTotal () {
        return movimientoDao.getTotalCategoria(null);
    }

    public double getTotalCategoria (Categoria categoria) {
        return movimientoDao.getTotalCategoria(categoria.getCodigo());
    }

    public void update(Gasto gasto) {
        movimientoDao.update(new Movimiento(gasto));
    }

    public void eliminar(Gasto gasto) {
        movimientoDao.delete(new Movimiento(gasto));
    }

    public void guardar(Gasto gasto) {
        movimientoDao.add(new Movimiento(gasto));
    }
}
