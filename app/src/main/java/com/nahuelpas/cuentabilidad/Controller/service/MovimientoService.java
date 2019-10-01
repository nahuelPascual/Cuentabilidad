package com.nahuelpas.cuentabilidad.Controller.service;

import android.widget.EditText;
import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Controller.mapper.*;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.CobranzaService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.CompraDivisaService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.GastoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.IngresoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.PrestamoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.TransferenciaService;
import com.nahuelpas.cuentabilidad.Controller.validator.*;

import java.util.List;
import java.util.Map;

public abstract class MovimientoService<T> {

    public static final String SPINNER_CATEG = "spinnerCategoria" ;
    public static final String SPINNER_CUENTA = "spinnerCuenta" ;
    public static final String SPINNER_CUENTA2 = "spinnerCuenta2" ;
    public static final String DESCRIPCION = "descripcion" ;
    public static final String MONTO = "monto" ;
    public static final String MONTO2 = "monto2" ;
    public static final String PARAM_TIPO_MOVIMIENTO = "tipoGasto";

    protected CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected CategoriaDao categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected MovimientoMapper movimientoMapper = new MovimientoMapper();
    protected CuentaService cuentaService = new CuentaService();
    protected Validator validator = new Validator();

    public static MovimientoService getInstancia (Movimiento.Tipo tipo) {
        switch (tipo) {
            case GASTO:
                return new GastoService();
            case INGRESO:
                return new IngresoService();
            case PRESTAMO:
                return new PrestamoService();
            case COBRANZA:
                return new CobranzaService();
            case TRANSFERENCIA:
                return new TransferenciaService();
            case COMPRA_DIVISA:
                return new CompraDivisaService();
            default:
                return null;
        }
    }

    public MovimientoBase mapearMovimiento(Movimiento mov) {
        switch (mov.getTipo()) {
            case GASTO:
                return movimientoMapper.mappearGasto(mov);
            case INGRESO:
                return movimientoMapper.mappearIngreso(mov);
            case PRESTAMO:
                return movimientoMapper.mappearPrestamo(mov);
            case COBRANZA:
                return movimientoMapper.mappearCobranza(mov);
            case TRANSFERENCIA:
                return movimientoMapper.mappearTransferencia(mov);
            case COMPRA_DIVISA:
                return movimientoMapper.mappearCompraDivisa(mov);
            default:
                return null;
        }
    }

    /* Cada movimiento implementa su carga */
    public abstract Movimiento cargarMovimiento(Map<String, Object> elementos) throws ValidationException;

    /* Carga los atributos comunes a todos los movimientos */
    protected void cargarMovimiento(Map<String, Object> elementos, MovimientoBase movimientoBase, Object object){
        EditText descGasto = (EditText) elementos.get(DESCRIPCION),
                monto = (EditText) elementos.get(MONTO);
        Spinner spinnerCuenta = (Spinner) elementos.get(SPINNER_CUENTA);

        movimientoBase.setCodigo(movimientoDao.getNextId());
        movimientoBase.setDescripcion(descGasto!=null? descGasto.getText().toString() : null);
        movimientoBase.setMonto(Double.valueOf(monto.getText().toString()));
        movimientoBase.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
        verificarDescripcion(movimientoBase, object);
    }

    protected void verificarDescripcion(MovimientoBase movimientoBase, Object object){
        if(movimientoBase.getDescripcion() == null || movimientoBase.getDescripcion().isEmpty())
            movimientoBase.setDescripcion(getDefaultDescription(movimientoBase, object));
    }
    protected abstract String getDefaultDescription(MovimientoBase movimientoBase, Object object);

    public abstract void guardarMovimiento(T movimiento) throws ValidationException;
    public abstract void eliminarMovimiento(Movimiento movimiento) throws ValidationException; //TODO revisar mejor

    public double calcularTotal (List<Movimiento> movimientos) {
        double total = 0;
        int multiplicador;
//        List<MovimientoBase> movimientosBase = movimientoMapper.mappearMovimientos(movimientos);
        for (Movimiento mov : movimientos) {
            multiplicador = -1;
            if(Movimiento.Tipo.getPositivos().contains(mov.getTipo())){
                multiplicador = 1;
            }
            total += multiplicador*mov.getMonto();
        }
        return total;
    }
}
