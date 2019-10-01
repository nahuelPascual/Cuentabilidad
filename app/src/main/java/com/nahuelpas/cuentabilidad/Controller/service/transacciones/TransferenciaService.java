package com.nahuelpas.cuentabilidad.Controller.service.transacciones;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.TransferenciaDao;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Transferencia;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;

import java.util.Map;

public class TransferenciaService extends MovimientoService<Transferencia> {

    TransferenciaDao transferenciaDao = new TransferenciaDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) throws ValidationException {
        Cuenta cuentaDestino = cuentaDao.getCuentaByDesc( ((Spinner) elementos.get(SPINNER_CUENTA2)) .getSelectedItem().toString());
        Transferencia transferencia = new Transferencia();
        cargarMovimiento(elementos, transferencia, cuentaDestino);
        validator.validarOrigenDestino(transferencia.getIdCuenta(), cuentaDestino.getCodigo());
        transferencia.setCuentaTransferencia(cuentaDestino.getCodigo());
        return new Movimiento(transferencia);
    }

    @Override
    protected String getDefaultDescription(MovimientoBase movimientoBase, Object cuentaDestino) {
        return cuentaDao.getById(movimientoBase.getIdCuenta()).getDescripcion()
                + " -> " + ((Cuenta) cuentaDestino).getDescripcion();
    }

    @Override
    public void eliminarMovimiento(Movimiento transferencia) throws ValidationException {
        cuentaService.egresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getIdCuentaAlt()));
        cuentaService.ingresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getIdCuenta()));
//        transferenciaDao.eliminar(transferencia);
        movimientoDao.delete(transferencia);
    }

    @Override
    public void guardarMovimiento(Transferencia transferencia) throws ValidationException {
        cuentaService.egresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getIdCuenta()));
        cuentaService.ingresarDinero(transferencia.getMonto(), cuentaDao.getById(transferencia.getCuentaTransferencia()));
        transferenciaDao.guardar(transferencia);
    }


}
