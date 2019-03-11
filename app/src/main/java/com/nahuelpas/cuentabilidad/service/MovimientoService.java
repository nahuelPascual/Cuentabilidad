package com.nahuelpas.cuentabilidad.service;

import android.widget.EditText;
import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.MovimientoBase;

import java.util.Map;

public abstract class MovimientoService<T> {

    public static final String  SPINNER_CATEG = "spinnerCategoria" ;
    public static final String  SPINNER_CUENTA = "spinnerCuenta" ;
    public static final String  SPINNER_CUENTA2 = "spinnerCuenta2" ;
    public static final String  DESCRIPCION = "descripcion" ;
    public static final String  MONTO = "monto" ;
    public static final String  MONTO2 = "monto2" ;

    protected CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected MovimientoDao movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected CategoriaDao categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected MovimientoMapper movimientoMapper = new MovimientoMapper();
    protected CuentaService cuentaService = new CuentaService();

   /* public static MovimientoService getInstance(Movimiento.Tipo tipo) {
        switch (tipo) {
            case GASTO:
                return new GastoService();
            case INGRESO:
                return new IngresoService();
            case PRESTAMO:
                return new PrestamoService();
            case COBRANZA:
                return new CobranzaService();
            default:
                return null;
        }
    } */

    /* Cada movimiento implementa su carga */
    public abstract Movimiento cargarMovimiento(Map<String, Object> elementos);

    /* Carga los atributos comunes a todos los movimientos */
    protected void cargarMovimiento(Map<String, Object> elementos, MovimientoBase movimientoBase){
        EditText descGasto = (EditText) elementos.get(DESCRIPCION),
                monto = (EditText) elementos.get(MONTO);
        Spinner spinnerCuenta = (Spinner) elementos.get(SPINNER_CUENTA);

        movimientoBase.setCodigo(movimientoDao.getNextId());
        movimientoBase.setDescripcion(descGasto!=null? descGasto.getText().toString() : null);
        movimientoBase.setMonto(Double.valueOf(monto.getText().toString()));
        movimientoBase.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
    }

    public abstract void guardarMovimiento(T movimiento) throws ValidationException;
    public abstract void eliminarMovimiento(T movimiento) throws ValidationException;
}
