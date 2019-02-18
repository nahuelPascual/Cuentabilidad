package com.nahuelpas.cuentabilidad.service;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

public abstract class MovimientoService {

    protected CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
    protected GastoDao gastoDao = new GastoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

    public static MovimientoService getInstance(Gasto.Tipo tipo) {
        switch (tipo) {
            case GASTO:
                return new GastoService();
            case INGRESO:
                return new IngresoService();
            case PRESTAMO:
                return new PrestamoService();
            case PAGO:
                return new PagoService();
            default:
                return null;
        }
    }

}
