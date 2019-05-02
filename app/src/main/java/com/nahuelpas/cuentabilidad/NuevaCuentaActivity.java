package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.service.transacciones.GastoService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaCuentaActivity extends AppCompatActivity {

    Button save;
    CuentaDao cuentaDao;
    EditText nombreCuenta, saldoCuenta;
    CheckBox checkBoxPrestamo, checkBoxDolares;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);

        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        nombreCuenta = findViewById(R.id.et_nombreCuentaNueva);
        saldoCuenta = findViewById(R.id.et_saldoCuentaNueva);
        save = findViewById(R.id.btn_guardarCuenta);
        checkBoxPrestamo = findViewById(R.id.chkBox_CtaPrestamo);
        checkBoxDolares = findViewById(R.id.chkBox_CtaDolares);

        final Intent i = new Intent(getApplicationContext(), (Class) getIntent().getExtras().get("class"));

        /* Si la nueva cuenta se agrega desde la Activity NuevoPrestamo o NuevoCobranza */
        Movimiento.Tipo tipoMovimiento = (Movimiento.Tipo) getIntent().getExtras().get(GastoService.PARAM_TIPO_MOVIMIENTO);
        if(Movimiento.Tipo.PRESTAMO.equals(tipoMovimiento) || Movimiento.Tipo.COBRANZA.equals(tipoMovimiento)){
            checkBoxPrestamo.setChecked(true);
            checkBoxPrestamo.setEnabled(false);
            saldoCuenta.setText("0");
            saldoCuenta.setEnabled(false);
            i.putExtra(GastoService.PARAM_TIPO_MOVIMIENTO, tipoMovimiento);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreCuenta.getText().toString();
                String saldo = saldoCuenta.getText().toString();
                if (nombre!=null && !nombre.isEmpty() && saldo!=null && !saldo.isEmpty()) {
                    cuentaDao.add(new Cuenta(cuentaDao.getNextId(),
                            nombre, Double.parseDouble(saldo), checkBoxPrestamo.isChecked(),
                            checkBoxDolares.isChecked()? Cuenta.Moneda.DOLARES : Cuenta.Moneda.PESOS));

                    Toast.makeText(getApplicationContext(), "Cuenta guardada exitosamente.", Toast.LENGTH_SHORT).show();

                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Revise los datos ingresados.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
