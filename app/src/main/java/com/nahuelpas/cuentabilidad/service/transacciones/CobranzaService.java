package com.nahuelpas.cuentabilidad.service.transacciones;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.transacciones.CobranzaDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.service.MovimientoService;

import java.util.Map;

public class CobranzaService extends MovimientoService<Cobranza> {

    CobranzaDao cobranzaDao = new CobranzaDao();

    @Override
    public void guardarMovimiento(Cobranza cobranza) {
        cuentaService.ingresarDinero(cobranza.getMonto(), cuentaDao.getById(cobranza.getIdCuenta()));
        cuentaService.ingresarDinero(cobranza.getMonto()*(-1), cuentaDao.getById(cobranza.getIdCuentaPrestamo())); // puedo deber plata
        cobranzaDao.guardar(cobranza);
    }

    @Override
    public void eliminarMovimiento(Movimiento cobranza) throws ValidationException {
        cuentaService.egresarDinero(cobranza.getMonto(), cuentaDao.getById(cobranza.getIdCuenta()));
        cuentaService.ingresarDinero(cobranza.getMonto(), cuentaDao.getById(cobranza.getIdCuentaAlt()));
//        cobranzaDao.eliminar(cobranza);
        movimientoDao.delete(cobranza);
    }

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);
        Cobranza cobranza = new Cobranza();
        cargarMovimiento(elementos, cobranza);
        cobranza.setIdCuentaPrestamo(cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString()).getCodigo());
        return new Movimiento(cobranza);
    }
}
