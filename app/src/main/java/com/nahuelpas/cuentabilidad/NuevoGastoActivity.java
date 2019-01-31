package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.exception.BusinessException;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoGastoActivity extends AppCompatActivity {

    private Gasto gasto = new Gasto();
    private GastoDao gastoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, saldo;
    private Button btn_saveGasto;
    private Spinner spinnerCategoria, spinnerCuenta;

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
        saldo = findViewById(R.id.et_montoGasto);
        btn_saveGasto = findViewById(R.id.btn_guardarGasto);
        btn_saveGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarGasto();
            }
        });
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        spinnerCuenta = (Spinner) findViewById(R.id.spinnerCuenta);

        try {
            gasto = gastoDao.getById(getIntent().getExtras().getLong("idGasto"));
            if(gasto!=null) {
                spinnerCategoria.setSelection(gasto.getIdCategoria().intValue()); //TODO va a setear cualquiera
                spinnerCuenta.setSelection(gasto.getIdCuenta().intValue()); //TODO va a setear cualquiera
                descGasto.setText(gasto.getDescripcion());
                saldo.setText(String.valueOf(gasto.getMonto()));
                getIntent().putExtra("montoAnterior", gasto.getMonto());
            }
        } catch (Exception e){
            gasto = new Gasto();
        }

        addItemsOnSpinner();
    }

    private void guardarGasto() {
        if ("".equals(saldo.getText().toString())) {
            Toast.makeText(this, "El gasto no puede tener monto vacío.", Toast.LENGTH_SHORT).show();
        } else {
            boolean nuevoGasto = false;
            if (gasto.getFecha() == null) {
                gasto.setFecha(new Date());
                gasto.setCodigo(gastoDao.getNextId());
                nuevoGasto = true;
            }
            gasto.setDescripcion(descGasto.getText().toString());
            gasto.setMonto(new Double(saldo.getText().toString()));
            gasto.setIdCategoria(categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo());
            gasto.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
            gasto.setTipo(Gasto.Tipo.GASTO);

            try {
                if (nuevoGasto){
                    actualizarSaldo(gasto.getMonto(), gasto.getIdCuenta());
                    gastoDao.add(gasto);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    actualizarSaldo(gasto.getMonto()-getIntent().getExtras().getDouble("montoAnterior"), gasto.getIdCuenta());
                    gastoDao.update(gasto);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            } catch (BusinessException e) {
                Toast.makeText(this, "Saldo insuficiente en la cuenta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void actualizarSaldo(double gasto, Long idCuenta) throws BusinessException {
        Cuenta cuenta = cuentaDao.getById(idCuenta);
        double nuevoSaldo = cuenta.getSaldo()-gasto;

        if(nuevoSaldo<=0) throw new BusinessException();

        cuenta.setSaldo(nuevoSaldo);
        cuentaDao.update(cuenta);
    }

    public void addItemsOnSpinner() {
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoriaDao.getDescripciones());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(catAdapter);

        ArrayAdapter<String> cuentaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones());
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta.setAdapter(cuentaAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
      //  spinnerCategoria.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
