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
import com.nahuelpas.cuentabilidad.exception.BusinessException;
import com.nahuelpas.cuentabilidad.model.Cuenta;
import com.nahuelpas.cuentabilidad.model.Gasto;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.util.StringUtil;

public class NuevoGastoActivity extends AppCompatActivity {

    private Spinner spinner;
    private GastoDao gastoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, saldo, categoria, cuenta;
    private Button btn_saveGasto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);

        /* inicializacion de DAOs */
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));

        /* inicializacion de elementos */
        descGasto = findViewById(R.id.et_descGasto);
        categoria = findViewById(R.id.et_codCategoria);
        saldo = findViewById(R.id.et_montoGasto);
        cuenta = findViewById(R.id.et_codCuenta);
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
            Long idCuenta =
                    !"".equals(cuenta.getText().toString())
                            ? new Long(cuenta.getText().toString())
                            : 1L; //FIXME borrar cuando funcione el combo de cuentas y haya una por default
            gasto.setIdCuenta(idCuenta);

            try {
                actualizarSaldo(gasto.getMonto(), gasto.getIdCuenta());
                gastoDao.add(gasto);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                limpiarCampos();
            } catch (BusinessException e) {
                Toast.makeText(this, "¡ERROR! SALDO INSUFICIENTE", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void actualizarSaldo(double gasto, Long idCuenta) throws BusinessException {
        Cuenta cuenta = cuentaDao.getById(idCuenta);
        double nuevoSaldo = cuenta.getSaldo()-gasto;

        if(nuevoSaldo<0) throw new BusinessException();

        cuenta.setSaldo(nuevoSaldo);
        cuentaDao.update(cuenta);
    }

    private void limpiarCampos() {
        descGasto.setText("");
        categoria.setText("");
        saldo.setText("");
        cuenta.setText("");
    }
}
