package com.nahuelpas.cuentabilidad.Controller.service.transacciones;

import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.IngresoDao;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;

import java.util.Map;

public class IngresoService extends MovimientoService<Ingreso> {

    IngresoDao ingresoDao = new IngresoDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Ingreso ingreso = new Ingreso();
        cargarMovimiento(elementos, ingreso, null);
        return new Movimiento(ingreso);
    }

    @Override
    protected String getDefaultDescription(MovimientoBase movimientoBase, Object object) {
        return "Ingreso " + cuentaDao.getById(movimientoBase.getIdCuenta()).getDescripcion();
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
