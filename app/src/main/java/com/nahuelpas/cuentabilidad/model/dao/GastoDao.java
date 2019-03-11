package com.nahuelpas.cuentabilidad.model.dao;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;

import java.util.List;

public class GastoDao {

    MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    MovimientoMapper movimientoMapper = new MovimientoMapper();

    public  Gasto getById(Long id) {
        return movimientoMapper.mappearGasto(movimientoDao.getById(id));
    }

    public List<Gasto> getAll() {
        return movimientoMapper.mappearGasto(movimientoDao.getAll(Movimiento.Tipo.GASTO.getValue()));
    }

    public List<Gasto> getByCategoria(Categoria categoria) {
        return movimientoMapper.mappearGasto(movimientoDao.getByCategoria(categoria.getCodigo(), Movimiento.Tipo.GASTO.getValue()));
    }

    public void update(Gasto gasto) {
        movimientoDao.update(new Movimiento(gasto));
    }

    public void eliminar(Gasto gasto) {
        movimientoDao.delete(new Movimiento(gasto));
    }
}
