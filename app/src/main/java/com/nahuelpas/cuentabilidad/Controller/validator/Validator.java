package com.nahuelpas.cuentabilidad.Controller.validator;

import android.widget.EditText;

import com.nahuelpas.cuentabilidad.model.exception.ValidationException;

public class Validator {

    public void validarMonto(EditText monto) throws ValidationException {
        if (monto.getText().toString().isEmpty())
            throw new ValidationException("Especifique el monto.");
        if (Double.valueOf(monto.getText().toString()) <= 0) {
            throw new ValidationException("El monto debe ser mayor a cero.");
        }
    }

    public void validarSaldoCuenta(double nuevoSaldo) throws ValidationException {
        if (nuevoSaldo < 0) throw new ValidationException("Saldo insuficiente en la cuenta.");

    }

    public void validarOrigenDestino(long origen, long destino) throws ValidationException {
        if (origen == destino)
            throw new ValidationException("La cuenta de destino es la misma cuenta de origen");
    }
}
