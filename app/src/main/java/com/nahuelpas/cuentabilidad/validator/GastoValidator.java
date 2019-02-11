package com.nahuelpas.cuentabilidad.validator;

import android.widget.EditText;

import com.nahuelpas.cuentabilidad.exception.ValidationException;

public class GastoValidator {

    public void validarMonto(EditText monto) throws ValidationException {
        if (monto.getText().toString().isEmpty())
            throw new ValidationException("Especifique el monto.");
    }

    public void validarSaldoCuenta(double nuevoSaldo) throws ValidationException{
        if(nuevoSaldo<0) throw new ValidationException("Saldo insuficiente en la cuenta.");

    }
}
