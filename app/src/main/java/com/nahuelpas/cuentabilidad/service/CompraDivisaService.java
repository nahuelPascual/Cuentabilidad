package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.CompraDivisaDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;

import java.util.Map;

public class CompraDivisaService extends MovimientoService<CompraDivisa> {

    CompraDivisaDao compraDivisaDao = new CompraDivisaDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerCuenta = (Spinner) elementos.get(SPINNER_CUENTA2);
        CompraDivisa compraDivisa = new CompraDivisa();
        cargarMovimiento(elementos, compraDivisa);
        compraDivisa.setIdCuentaDivisa(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
        return new Movimiento(compraDivisa);
    }

    @Override
    public void eliminarMovimiento(CompraDivisa compraDivisa) throws ValidationException {
        cuentaService.egresarDinero(compraDivisa.getMontoDivisa(), cuentaDao.getById(compraDivisa.getIdCuentaDivisa()));
        cuentaService.ingresarDinero(compraDivisa.getMonto(), cuentaDao.getById(compraDivisa.getIdCuenta()));
        compraDivisaDao.eliminar(compraDivisa);
    }

    @Override
    public void guardarMovimiento(CompraDivisa compraDivisa) throws ValidationException {
        cuentaService.egresarDinero(compraDivisa.getMonto(), cuentaDao.getById(compraDivisa.getIdCuenta()));
        cuentaService.ingresarDinero(compraDivisa.getMontoDivisa(), cuentaDao.getById(compraDivisa.getIdCuentaDivisa()));
        compraDivisaDao.guardar(compraDivisa);
    }
}
