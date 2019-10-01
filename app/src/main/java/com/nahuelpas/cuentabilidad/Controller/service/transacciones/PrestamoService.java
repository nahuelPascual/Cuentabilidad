package com.nahuelpas.cuentabilidad.Controller.service.transacciones;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.PrestamoDao;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;

import java.util.Map;

public class PrestamoService extends MovimientoService<Prestamo> {

    PrestamoDao prestamoDao = new PrestamoDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);
        Cuenta cuentaPrestamo = cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString());
        Prestamo prestamo = new Prestamo();
        cargarMovimiento(elementos, prestamo, cuentaPrestamo);
        prestamo.setIdCuentaPrestamo(cuentaPrestamo.getCodigo());
        return new Movimiento(prestamo);
    }

    @Override
    protected String getDefaultDescription(MovimientoBase movimientoBase, Object cuenta) {
        return ((Cuenta) cuenta).getDescripcion();
    }

    @Override
    public void eliminarMovimiento(Movimiento prestamo) {
        cuentaService.ingresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuenta()));
        cuentaService.ingresarDinero(prestamo.getMonto()*(-1), cuentaDao.getById(prestamo.getIdCuentaAlt()));
//        prestamoDao.eliminar(prestamo);
        movimientoDao.delete(prestamo);
    }

    @Override
    public void guardarMovimiento(Prestamo prestamo) throws ValidationException {
        cuentaService.egresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuenta()));
        cuentaService.ingresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuentaPrestamo()));
        prestamoDao.guardar(prestamo);
    }
}
