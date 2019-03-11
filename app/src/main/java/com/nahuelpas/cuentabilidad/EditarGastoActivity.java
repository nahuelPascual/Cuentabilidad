package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.service.CuentaService;
import com.nahuelpas.cuentabilidad.service.GastoService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditarGastoActivity extends AppCompatActivity {

    private Gasto gastoAnterior;
    private Gasto gasto;
    private GastoDao gastoDao;
    private GastoService gastoService = new GastoService();
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, saldo;
    private Button btn_saveGasto;
    private Spinner spinnerCategoria, spinnerCuenta;
    private CuentaService cuentaService = new CuentaService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);

        /* inicializacion de DAOs */
        gastoDao = new GastoDao();
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        gasto = new Gasto();

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
        addItemsOnSpinner();

        try {
            gastoAnterior = gastoDao.getById(getIntent().getExtras().getLong(GastoService.PARAM_ID_GASTO));
            spinnerCategoria.setSelection(gastoService.getPosicionItemSpinner(spinnerCategoria, categoriaDao.getById(gastoAnterior.getIdCategoria()).getDescripcion()));
            spinnerCuenta.setSelection(gastoService.getPosicionItemSpinner(spinnerCuenta, cuentaDao.getById(gastoAnterior.getIdCuenta()).getDescripcion()));
            descGasto.setText(gastoAnterior.getDescripcion());
            saldo.setText(String.valueOf(gastoService.formatearGasto(gastoAnterior.getMonto())));
        } catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "No existe el ID de Gasto buscado", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Algo se hizo mierda!", Toast.LENGTH_LONG).show();
        }

    }

    private void guardarGasto() {
        if (saldo.getText().toString().isEmpty()) {
            Toast.makeText(this, "El gasto no puede tener monto vacío.", Toast.LENGTH_LONG).show();
        } else {
            gasto.setFecha(gastoAnterior.getFecha());
            gasto.setCodigo(gastoAnterior.getCodigo());

            /* actualizo información del gasto */
            gasto.setDescripcion(descGasto.getText().toString());
            gasto.setMonto(new Double(saldo.getText().toString()));
            gasto.setIdCategoria(categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo());
            gasto.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());

            try {
                if(gasto.getIdCuenta() == gastoAnterior.getIdCuenta()){
                    cuentaService.egresarDinero(gasto.getMonto()-gastoAnterior.getMonto(),
                            cuentaDao.getById(gasto.getIdCuenta()));
                } else {
                    cuentaService.egresarDinero(gasto.getMonto(), cuentaDao.getById(gasto.getIdCuenta()));
                    cuentaService.ingresarDinero(gastoAnterior.getMonto(), cuentaDao.getById(gastoAnterior.getIdCuenta())); // esto estaba afuera del else, creo que estaba mal
                }
                gastoDao.update(gasto);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } catch (ValidationException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
