package com.nahuelpas.cuentabilidad.service;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.validator.Validator;

public class CuentaService {

    CuentaDao dao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

    public static final String PARAM_ID_CUENTA = "idCuenta";

    Validator validator = new Validator();

    public void actualizarSaldo(double monto, Cuenta cuenta) throws ValidationException {
        double nuevoSaldo = cuenta.getSaldo() - monto;
        validator.validarSaldoCuenta(nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);
        dao.update(cuenta);
    }
    public void actualizarSaldo(double monto, Cuenta cuenta, Cuenta prestamo) throws ValidationException {
        double nuevoSaldo = cuenta.getSaldo() - monto;
        double saldoPrestamo = prestamo.getSaldo() + monto;
        validator.validarSaldoCuenta(nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);
        prestamo.setSaldo(saldoPrestamo);
        dao.update(cuenta);
        dao.update(prestamo);
    }

    public void actualizarSaldoIngreso(double monto, Cuenta cuenta) {
        double nuevoSaldo = cuenta.getSaldo() + monto;
        cuenta.setSaldo(nuevoSaldo);
        dao.update(cuenta);
    }
    public void actualizarSaldoIngreso(double monto, Cuenta cuenta, Cuenta prestamo){
        double nuevoSaldo = cuenta.getSaldo() + monto;
        double saldoPrestamo = prestamo.getSaldo() - monto;
        cuenta.setSaldo(nuevoSaldo);
        prestamo.setSaldo(saldoPrestamo);
        dao.update(cuenta);
        dao.update(prestamo);
    }

    public void actualizarSaldoTransferencia(double montoOrigen, double montoDestino, Cuenta origen, Cuenta destino) throws ValidationException {
        actualizarSaldo(montoOrigen, origen);
        actualizarSaldoIngreso(montoDestino, destino);
    }
}
