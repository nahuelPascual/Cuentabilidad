package com.nahuelpas.cuentabilidad;

import android.content.Intent;
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
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.service.CuentaService;
import com.nahuelpas.cuentabilidad.service.GastoService;
import com.nahuelpas.cuentabilidad.service.MovimientoService;
import com.nahuelpas.cuentabilidad.validator.GastoValidator;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoGastoActivity extends AppCompatActivity {

    private Gasto.Tipo tipoMovimiento;

    private Gasto gasto = new Gasto();
    private GastoDao gastoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, monto;
    private Button btn_saveGasto;
    private Spinner spinnerCategoria, spinnerCuenta, spinnerPrestamo;
    //private MovimientoService movimientoService = MovimientoService.getInstance(tipoMovimiento); //TODO
    private CuentaService cuentaService = new CuentaService();
    private GastoValidator gastoValidator = new GastoValidator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tipoMovimiento = (Gasto.Tipo) getIntent().getExtras().get(GastoService.PARAM_TIPO_GASTO);

        /* inicializacion de DAOs */
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));

        switch(tipoMovimiento){
            case GASTO:
                setContentView(R.layout.activity_nuevo_gasto);
                initSpinnerCategoria();
                break;
            case INGRESO:
                setContentView(R.layout.activity_nuevo_ingreso);
                break;
            case PRESTAMO:
            case PAGO:
                setContentView(R.layout.activity_nuevo_prestamo);
                initSpinnerPrestamo();
                break;
        }

        /* inicializacion de elementos comunes */
        initSpinnerCuenta();
        descGasto = findViewById(R.id.et_descGasto);
        monto = findViewById(R.id.et_montoGasto);
        btn_saveGasto = findViewById(R.id.btn_guardarGasto);
        btn_saveGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    gastoValidator.validarMonto(monto);
                    cargarMovimiento();
                    actualizarsSaldo();
                    gastoDao.add(gasto);
                    startActivity(new Intent(NuevoGastoActivity.this, MainActivity.class));
                } catch (ValidationException e) {
                    Toast.makeText(NuevoGastoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSpinnerCategoria() {
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoriaDao.getDescripciones());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(catAdapter);
    }
    private void initSpinnerCuenta() {
        spinnerCuenta = findViewById(R.id.spinnerCuenta);
        ArrayAdapter<String> cuentaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones());
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta.setAdapter(cuentaAdapter);
    }
    private void initSpinnerPrestamo() {
        spinnerPrestamo = findViewById(R.id.spinnerPrestamo);
        ArrayAdapter<String> prestamoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripcionesPrestamo());
        prestamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta.setAdapter(prestamoAdapter);
    }

    private void cargarMovimiento() {
        gasto.setFecha(new Date());
        gasto.setCodigo(gastoDao.getNextId());
        gasto.setDescripcion(descGasto.getText().toString());
        gasto.setMonto(Double.valueOf(monto.getText().toString()));
        gasto.setIdCategoria(spinnerCategoria!=null? categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo() :null);
        gasto.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());
        gasto.setTipo(tipoMovimiento);
    }

    private void actualizarsSaldo() throws ValidationException {
        Cuenta cuenta = cuentaDao.getById(gasto.getIdCuenta());
        switch(tipoMovimiento){
            case GASTO:
                cuentaService.actualizarSaldo(gasto.getMonto(), cuenta);
                break;
            case INGRESO:
                cuentaService.actualizarSaldoIngreso(gasto.getMonto(),cuenta);
                break;
            case PRESTAMO:
                cuentaService.actualizarSaldo(gasto.getMonto(), cuenta, cuentaDao.getCuentaByDesc((String)spinnerPrestamo.getSelectedItem()));
                break;
            case PAGO:
                cuentaService.actualizarSaldoIngreso(gasto.getMonto(), cuenta, cuentaDao.getCuentaByDesc((String)spinnerPrestamo.getSelectedItem()));
        }
    }
}
