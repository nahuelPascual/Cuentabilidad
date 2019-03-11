package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.TransferenciaDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

import java.util.Map;

public class TransferenciaService extends MovimientoService<Transferencia> {

    TransferenciaDao transferenciaDao = new TransferenciaDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerTransferencia = (Spinner) elementos.get(SPINNER_CUENTA2);
        Transferencia transferencia = new Transferencia();
        cargarMovimiento(elementos, transferencia);
        transferencia.setCuentaTransferencia(cuentaDao.getCuentaByDesc(spinnerTransferencia.getSelectedItem().toString()).getCodigo());
        return new Movimiento(transferencia);
    }

    @Override
    public void eliminarMovimiento(Transferencia transferencia) throws ValidationException {
        cuentaService.egresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getCuentaTransferencia()));
        cuentaService.ingresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getIdCuenta()));
        transferenciaDao.eliminar(transferencia);
    }

    @Override
    public void guardarMovimiento(Transferencia transferencia) throws ValidationException {
        cuentaService.egresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getIdCuenta()));
        cuentaService.ingresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getCuentaTransferencia()));
        transferenciaDao.guardar(transferencia);
    }
}
