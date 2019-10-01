package com.nahuelpas.cuentabilidad.Controller.service;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.View.layout.MainActivity;
import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Controller.validator.*;

public class CuentaService {

    CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

    public static final String PARAM_ID_CUENTA = "idCuenta";

    Validator validator = new Validator();

    public void egresarDinero(double monto, Cuenta cuenta) throws ValidationException {
        double nuevoSaldo = cuenta.getSaldo() - monto;
        validator.validarSaldoCuenta(nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);
        cuentaDao.update(cuenta);
    }
    public void egresarDineroPrestamo(double monto, Cuenta cuenta, Cuenta prestamo) throws ValidationException {
        egresarDinero(monto, cuenta);
        ingresarDinero(monto, prestamo);
    }

    public void ingresarDinero(double monto, Cuenta cuenta) {
        double nuevoSaldo = cuenta.getSaldo() + monto;
        cuenta.setSaldo(nuevoSaldo);
        cuentaDao.update(cuenta);
    }
    public void ingresarDineroCobranza(double monto, Cuenta cuenta, Cuenta prestamo){
        ingresarDinero(monto, cuenta);
        ingresarDinero(monto*(-1), prestamo); // no se valida porque puede quedar en negativo,
                                                    // lo que significaría prestamo otorgado al usuario.
    }

    public void transferirDinero(double montoOrigen, double montoDestino/*TODO*/, Cuenta origen, Cuenta destino) throws ValidationException {
        egresarDinero(montoOrigen, origen);
        ingresarDinero(montoDestino, destino);
    }
}
