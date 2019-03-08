package com.nahuelpas.cuentabilidad.service;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GenericDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;

import java.util.Date;
import java.util.Map;

public abstract class MovimientoService {

    public static final String  SPINNER_CATEG = "spinnerCategoria" ;
    public static final String  SPINNER_CUENTA = "spinnerCuenta" ;
    public static final String  SPINNER_CUENTA2 = "spinnerCuenta2" ;
    public static final String  SPINNER_PRESTAMO = "spinnerPrestamo" ;
    public static final String  DESCRIPCION = "descripcion" ;
    public static final String  MONTO = "monto" ;
    public static final String  MONTO2 = "monto2" ;
    public static final String  ID_CODIGO = "codigo" ;

    protected CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected GastoDao gastoDao = new GastoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

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

    public void cargarMovimiento(Map<String, Object> elementos) {
        gasto.setFecha(new Date());
        gasto.setAnio_mes(gastoMapper.toAnioMes(gasto.getFecha()));
        gasto.setCodigo(gastoDao.getNextId());
        gasto.setDescripcion(descGasto!=null? descGasto.getText().toString() : null);
        gasto.setMonto(Double.valueOf(monto.getText().toString()));
        gasto.setIdCategoria(spinnerCategoria!=null? categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo() :null);
        gasto.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
        gasto.setTipo(tipoMovimiento);
    }

}
