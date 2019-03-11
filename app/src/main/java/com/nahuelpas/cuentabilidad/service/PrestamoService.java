package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.PrestamoDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;

import java.util.Map;

public class PrestamoService extends MovimientoService<Prestamo> {

    PrestamoDao prestamoDao = new PrestamoDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);

        Prestamo prestamo = new Prestamo();
        cargarMovimiento(elementos, prestamo);
        prestamo.setIdCuentaPrestamo(cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString()).getCodigo());
        return new Movimiento(prestamo);
    }

    @Override
    public void eliminarMovimiento(Prestamo prestamo) {
        cuentaService.ingresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuenta()));
        cuentaService.ingresarDinero(prestamo.getMonto()*(-1), cuentaDao.getById(prestamo.getIdCuentaPrestamo()));
        prestamoDao.eliminar(prestamo);
    }

    @Override
    public void guardarMovimiento(Prestamo prestamo) throws ValidationException {
        cuentaService.egresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuenta()));
        cuentaService.ingresarDinero(prestamo.getMonto(), cuentaDao.getById(prestamo.getIdCuentaPrestamo()));
        prestamoDao.guardar(prestamo);
    }

}
