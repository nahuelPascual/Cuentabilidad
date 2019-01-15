package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.dao.GastoDao;
import com.nahuelpas.cuentabilidad.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.Cuenta;
import com.nahuelpas.cuentabilidad.model.Gasto;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoGastoActivity extends AppCompatActivity {

    private Spinner spinner;
    private GastoDao gastoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, saldo, categoria;
    private Button btn_saveGasto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);

        /* inicializacion de DAOs */
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));

        /* inicializacion de elementos */
        descGasto = findViewById(R.id.et_descGasto);
        categoria = findViewById(R.id.et_codCategoria);
        saldo = findViewById(R.id.et_montoGasto);
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        btn_saveGasto = findViewById(R.id.btn_guardarGasto);
        btn_saveGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarGasto();
            }
        });

//        spinner = findViewById(R.id.tipoGastoSpinner);
//        RecyclerView catSpin = findViewById(R.id.recyclerView);
//        SimpleCursorAdapter adapter =
//                new SimpleCursorAdapter(this, R.layout.categorias_spinner, categoriaDao.getCategoriasCursor(),
//                new String[] {"_id"}, new int[] {0},0);
//        spinner.setAdapter(adapter);
    }

    private void guardarGasto() {
        if ("".equals(saldo.getText().toString())) {
            Toast.makeText(this, "El gasto no puede tener monto vacío.", Toast.LENGTH_SHORT).show();
        } else {
            Gasto gasto = new Gasto();
            gasto.setCodigo(gastoDao.getNextId());
            gasto.setFecha(new Date());
            gasto.setDescripcion(descGasto.getText().toString());
            gasto.setMonto(new Double(saldo.getText().toString()));
            Long idCategoria =
                    !"".equals(categoria.getText().toString())
                            ? new Long(categoria.getText().toString())
                            : 1L; //FIXME borrar cuando funcione el combo de categorias y haya una por default
            gasto.setIdCategoria(idCategoria);

            gastoDao.add(gasto);
            actualizarSaldo(gasto.getMonto(), gasto.getIdCuenta());

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private void actualizarSaldo(double gasto, Long idCuenta) {
        Cuenta cuenta = cuentaDao.getById(idCuenta);
        cuenta.setSaldo(cuenta.getSaldo()-gasto);
        cuentaDao.update(cuenta);
    }
}
