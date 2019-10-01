package com.nahuelpas.cuentabilidad.View.layout;

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
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.exception.ValidationException;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.TransaccionDao;
import com.nahuelpas.cuentabilidad.Model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.Controller.service.CuentaService;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.GastoService;
import com.nahuelpas.cuentabilidad.Controller.utils.Utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditarGastoActivity extends AppCompatActivity {

    private MovimientoBase movimientoAnterior;
    private Movimiento movimientoNuevo = new Movimiento();
    private TransaccionDao transaccionDao;
    private MovimientoService movimientoService;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;
    private EditText descGasto, saldo;
    private Button btn_saveGasto;
    private Spinner spinnerCategoria, spinnerCuenta;
    private CuentaService cuentaService = new CuentaService();
    private Movimiento.Tipo tipoMovimiento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);
        tipoMovimiento = getIntent().getExtras() != null
                ? (Movimiento.Tipo) getIntent().getExtras().get(MovimientoService.PARAM_TIPO_MOVIMIENTO)
                : null;

        try {

            /* inicializacion de DAOs */
            transaccionDao = MovimientoDao.getInstancia(tipoMovimiento);
            cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
            categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));
//            movimientoNuevo = MovimientoService.getInstancia(tipoMovimiento);

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

            movimientoAnterior = transaccionDao.getById(getIntent().getExtras().getLong(GastoService.PARAM_ID_GASTO));
//            spinnerCategoria.setSelection(Utils.getPosicionItemSpinner(spinnerCategoria, categoriaDao.getById(movimientoAnterior.getIdCategoria()).getDescripcion()));
            spinnerCuenta.setSelection(Utils.getPosicionItemSpinner(spinnerCuenta, cuentaDao.getById(movimientoAnterior.getIdCuenta()).getDescripcion()));
            descGasto.setText(movimientoAnterior.getDescripcion());
            saldo.setText(String.valueOf(/*movimientoService.formatearGasto(*/movimientoAnterior.getMonto())/*)*/);
        } catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "No existe el ID de Movimiento buscado", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Algo se hizo mierda!", Toast.LENGTH_LONG).show();
        }
    }

    private void guardarGasto() {
        if (saldo.getText().toString().isEmpty()) {
            Toast.makeText(this, "El movimiento no puede tener monto vacío.", Toast.LENGTH_LONG).show();
        } else {
            movimientoNuevo.setFecha(movimientoAnterior.getFecha());
            movimientoNuevo.setCodigo(movimientoAnterior.getCodigo());

            /* actualizo información del movimientoNuevo */
            movimientoNuevo.setDescripcion(descGasto.getText().toString());
            movimientoNuevo.setMonto(Double.valueOf(saldo.getText().toString()));
            movimientoNuevo.setIdCategoria(categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo());
            movimientoNuevo.setIdCuenta(cuentaDao.getCuentaByDesc(spinnerCuenta.getSelectedItem().toString()).getCodigo());

            try {
                if(movimientoNuevo.getIdCuenta() == movimientoAnterior.getIdCuenta()){
                    cuentaService.egresarDinero(movimientoNuevo.getMonto()- movimientoAnterior.getMonto(),
                            cuentaDao.getById(movimientoNuevo.getIdCuenta()));
                } else {
                    cuentaService.egresarDinero(movimientoNuevo.getMonto(), cuentaDao.getById(movimientoNuevo.getIdCuenta()));
                    cuentaService.ingresarDinero(movimientoAnterior.getMonto(), cuentaDao.getById(movimientoAnterior.getIdCuenta())); // esto estaba afuera del else, creo que estaba mal
                }
//                gastoDao.update(movimientoNuevo);
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
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones(Cuenta.Moneda.PESOS.getValue()));
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta.setAdapter(cuentaAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
      //  spinnerCategoria.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
