package com.nahuelpas.cuentabilidad.service.transacciones;

import android.widget.Spinner;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.transacciones.TransferenciaDao;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;
import com.nahuelpas.cuentabilidad.service.MovimientoService;

import java.util.Map;

public class TransferenciaService extends MovimientoService<Transferencia> {

    TransferenciaDao transferenciaDao = new TransferenciaDao();

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) throws ValidationException {
        Cuenta cuentaDestino = cuentaDao.getCuentaByDesc( ((Spinner) elementos.get(SPINNER_CUENTA2)) .getSelectedItem().toString());
        Transferencia transferencia = new Transferencia();
        cargarMovimiento(elementos, transferencia);
        validator.validarOrigenDestino(transferencia.getIdCuenta(), cuentaDestino.getCodigo());
        transferencia.setCuentaTransferencia(cuentaDestino.getCodigo());
        transferencia.setDescripcion(cuentaDao.getById(transferencia.getIdCuenta()).getDescripcion()
                + " -> " + cuentaDestino.getDescripcion());
        return new Movimiento(transferencia);
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
