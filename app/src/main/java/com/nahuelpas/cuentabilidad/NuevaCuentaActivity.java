package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaCuentaActivity extends AppCompatActivity {

    Button save;
    CuentaDao cuentaDao;
    EditText nombreCuenta, saldoCuenta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);

        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        nombreCuenta = findViewById(R.id.et_nombreCuentaNueva);
        saldoCuenta = findViewById(R.id.et_saldoCuentaNueva);
        save = findViewById(R.id.btn_guardarCuenta);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreCuenta.getText().toString();
                String saldo = saldoCuenta.getText().toString();
                if (nombre!=null && !nombre.isEmpty() && saldo!=null && !saldo.isEmpty()) {
                    cuentaDao.add(new Cuenta(cuentaDao.getNextId(),
                            nombre, Double.parseDouble(saldo), false, Cuenta.Moneda.PESOS)); //TODO dar opciones
                    Toast.makeText(getApplicationContext(), "Cuenta guardada exitosamente.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Revise los datos ingresados.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
