package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.service.CuentaService;

import java.text.DateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleCuentaActivity extends AppCompatActivity {

    TextView monto, descripcion, tipo, subtipo, fecha;
    Button editar, eliminar;
    Cuenta cuenta;
    GastoDao gastoDao;
    CuentaDao cuentaDao;
    CategoriaDao categoriaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));

        cuenta = cuentaDao.getById(getIntent().getExtras().getLong(CuentaService.PARAM_ID_CUENTA));
        setTitle("Detalle " + cuenta.getDescripcion());

        /*
        monto = findViewById(R.id.detGasto_monto);
        fecha = findViewById(R.id.detGasto_fecha);
        descripcion = findViewById(R.id.detGasto_descr);
        tipo = findViewById(R.id.detGasto_subtipo);
        subtipo = findViewById(R.id.detGasto_subtipo);
        */

        eliminar = findViewById(R.id.btn_detCuenta_eliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try { // TODO pedir confirmación
                    cuentaDao.delete(cuenta);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Algo falló...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        fecha.setText(DateFormat.getDateInstance(DateFormat.ERA_FIELD).format(cuenta.getFecha()));
        descripcion.setText(cuenta.getDescripcion());
        tipo.setText("Tipo");
        subtipo.setText(categoriaDao.getById(gasto.getIdCategoria()).getDescripcion());
        */
    }
}
