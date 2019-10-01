package com.nahuelpas.cuentabilidad.Controller.service.transacciones;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.CobranzaDao;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;

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
    protected String getDefaultDescription(MovimientoBase movimientoBase, Object cuenta) {
        return ((Cuenta) cuenta).getDescripcion();
    }

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerPrestamo = (Spinner) elementos.get(SPINNER_CUENTA2);
        Cobranza cobranza = new Cobranza();
        Cuenta cuentaPrestamo = cuentaDao.getCuentaByDesc(spinnerPrestamo.getSelectedItem().toString());
        cargarMovimiento(elementos, cobranza, cuentaPrestamo);
        cobranza.setIdCuentaPrestamo(cuentaPrestamo.getCodigo());
        return new Movimiento(cobranza);
    }
}
