package com.nahuelpas.cuentabilidad.View.layout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Controller.service.CuentaService;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.GastoService;

import java.text.DateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleGastoActivity extends AppCompatActivity {

    TextView monto, descripcion, cuenta, tipo, subtipo, fecha;
    Button editar, eliminar;
    Movimiento gasto;
    MovimientoDao movimientoDao;
    CuentaDao cuentaDao;
    CategoriaDao categoriaDao;
    CuentaService cuentaService = new CuentaService();
    MovimientoService movimientoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gasto);

        movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));

        gasto = movimientoDao.getById(getIntent().getExtras().getLong(GastoService.PARAM_ID_GASTO));
        movimientoService = MovimientoService.getInstancia(gasto.getTipo());
        String movimiento = gasto.getTipo().toString().substring(0,1) + gasto.getTipo().toString().substring(1).toLowerCase();
        setTitle("Detalle " + movimiento);

        monto = findViewById(R.id.detGasto_monto);
        fecha = findViewById(R.id.detGasto_fecha);
        descripcion = findViewById(R.id.detGasto_descr);
        cuenta = findViewById(R.id.detGasto_cuenta);
        tipo = findViewById(R.id.detGasto_tipo);
        subtipo = findViewById(R.id.detGasto_subtipo);

        editar = findViewById(R.id.btn_detGasto_editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditarGastoActivity.class);
                i.putExtra(GastoService.PARAM_ID_GASTO, gasto.getCodigo());
                i.putExtra(GastoService.PARAM_TIPO_MOVIMIENTO, gasto.getTipo());
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
                            try{
                                movimientoService.eliminarMovimiento(gasto);
                            } catch (ValidationException e) {
                                Toast.makeText(DetalleGastoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
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
        tipo.setText(gasto.getTipo().toString());
        subtipo.setText(gasto.getIdCategoria()!=null? categoriaDao.getById(gasto.getIdCategoria()).getDescripcion() : null);
    }
}
