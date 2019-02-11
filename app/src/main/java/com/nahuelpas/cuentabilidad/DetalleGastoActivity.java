package com.nahuelpas.cuentabilidad;

import android.content.DialogInterface;
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
import com.nahuelpas.cuentabilidad.service.GastoService;

import java.text.DateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleGastoActivity extends AppCompatActivity {

    TextView monto, descripcion, cuenta, tipo, subtipo, fecha;
    Button editar, eliminar;
    Gasto gasto;
    GastoDao gastoDao;
    CuentaDao cuentaDao;
    CategoriaDao categoriaDao;
    CuentaService cuentaService = new CuentaService();
    GastoService gastoService = new GastoService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gasto);

        gastoDao = new GastoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));

        gasto = gastoDao.getById(getIntent().getExtras().getLong(GastoService.PARAM_ID_GASTO));
        String movimiento = gasto.getTipo().toString().substring(0,1) + gasto.getTipo().toString().substring(1).toLowerCase();
        setTitle("Detalle " + movimiento);

        /*//TODO borrar si anda bien
        if (Gasto.Tipo.GASTO.getValue() != gasto.getTipo().getValue()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {*/
            monto = findViewById(R.id.detGasto_monto);
            fecha = findViewById(R.id.detGasto_fecha);
            descripcion = findViewById(R.id.detGasto_descr);
            cuenta = findViewById(R.id.detGasto_cuenta);
            tipo = findViewById(R.id.detGasto_subtipo);
            subtipo = findViewById(R.id.detGasto_subtipo);

            editar = findViewById(R.id.btn_detGasto_editar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), EditarGastoActivity.class);
                    i.putExtra(GastoService.PARAM_ID_GASTO, gasto.getCodigo());
                    startActivity(i);
                }
            });
            eliminar = findViewById(R.id.btn_detGasto_eliminar);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(DetalleGastoActivity.this);
                        builderSingle.setTitle("Eliminar");
                        builderSingle.setMessage("¿Eliminar el gasto " + gasto.getDescripcion() + " ?");
                        builderSingle.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gastoService.eliminarGasto(gasto);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        });
                        builderSingle.show();

                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Algo falló...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            editar.setText("Editar " + movimiento);
            eliminar.setText("Eliminar " + movimiento);
            monto.setText(String.valueOf(gasto.getMonto()));
            fecha.setText(DateFormat.getDateInstance(DateFormat.ERA_FIELD).format(gasto.getFecha()));
            descripcion.setText(gasto.getDescripcion());
            cuenta.setText(gasto.getIdCuenta()!=null? cuentaDao.getById(gasto.getIdCuenta()).getDescripcion() : null);
            tipo.setText("Tipo");
            subtipo.setText(gasto.getIdCategoria()!=null? categoriaDao.getById(gasto.getIdCategoria()).getDescripcion() : null);
        //TODO borrar}
    }
}
