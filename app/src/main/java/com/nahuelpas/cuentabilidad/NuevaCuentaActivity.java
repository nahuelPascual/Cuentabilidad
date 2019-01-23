package com.nahuelpas.cuentabilidad;

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
                // TODO validar campos vacíos
                cuentaDao.add(new Cuenta(cuentaDao.getNextId(),
                        nombreCuenta.getText().toString(), Double.parseDouble(saldoCuenta.getText().toString())));
                Toast.makeText(getApplicationContext(), "Cuenta guardada exitosamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
