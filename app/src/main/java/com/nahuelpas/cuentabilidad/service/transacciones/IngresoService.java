package com.nahuelpas.cuentabilidad.service.transacciones;

import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.transacciones.IngresoDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.service.MovimientoService;

import java.util.Map;

public class IngresoService extends MovimientoService<Ingreso> {

    IngresoDao ingresoDao = new IngresoDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Ingreso ingreso = new Ingreso();
        cargarMovimiento(elementos, ingreso);
        return new Movimiento(ingreso);
    }

    @Override
    public void eliminarMovimiento(Movimiento ingreso) throws ValidationException {
        cuentaService.egresarDinero(ingreso.getMonto(), cuentaDao.getById(ingreso.getIdCuenta()));
//        ingresoDao.eliminar(ingreso);
        movimientoDao.delete(ingreso);
    }

    @Override
    public void guardarMovimiento(Ingreso ingreso) {
        cuentaService.ingresarDinero(ingreso.getMonto(), cuentaDao.getById(ingreso.getIdCuenta()));
        ingresoDao.guardar(ingreso);
    }

}
