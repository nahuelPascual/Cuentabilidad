package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.exception.ValidationException;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;
import com.nahuelpas.cuentabilidad.service.CuentaService;
import com.nahuelpas.cuentabilidad.service.transacciones.CompraDivisaService;
import com.nahuelpas.cuentabilidad.service.transacciones.GastoService;
import com.nahuelpas.cuentabilidad.service.transacciones.IngresoService;
import com.nahuelpas.cuentabilidad.service.MovimientoService;
import com.nahuelpas.cuentabilidad.service.transacciones.CobranzaService;
import com.nahuelpas.cuentabilidad.service.transacciones.PrestamoService;
import com.nahuelpas.cuentabilidad.service.transacciones.TransferenciaService;
import com.nahuelpas.cuentabilidad.validator.Validator;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoGastoActivity extends AppCompatActivity {

    private Movimiento.Tipo tipoMovimiento;
    private MovimientoBase movimiento;

    private MovimientoDao movimientoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;

    private Spinner spinnerCategoria, spinnerCuenta, spinnerCuenta2;
    private EditText descGasto, monto, monto2;
    private Button btn_saveGasto;
    private ImageView addPrestamo;

    private Validator validator = new Validator();
    private MovimientoMapper movimientoMapper = new MovimientoMapper();
    private CuentaService cuentaService = new CuentaService();

    private MovimientoService movimientoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tipoMovimiento = (Movimiento.Tipo) getIntent().getExtras().get(GastoService.PARAM_TIPO_MOVIMIENTO);

        /* inicializacion de DAOs */
        movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));

        switch(tipoMovimiento){
            case GASTO:
                setContentView(R.layout.activity_nuevo_gasto);
                movimiento = new Gasto();
                movimientoService = new GastoService();
                initSpinnerCategoria();
                break;
            case INGRESO:
                setContentView(R.layout.activity_nuevo_ingreso);
                movimiento = new Ingreso();
                movimientoService = new IngresoService();
                break;
            case PRESTAMO:
                setContentView(R.layout.activity_nuevo_prestamo);
                movimiento = new Prestamo();
                movimientoService = new PrestamoService();
                initBotonAgregarPrestamo();
                initSpinnerPrestamo();
                break;
            case COBRANZA:
                setContentView(R.layout.activity_nuevo_prestamo);
                movimiento = new Cobranza();
                movimientoService = new CobranzaService();
                initBotonAgregarPrestamo();
                initSpinnerPrestamo();
                break;
            case TRANSFERENCIA:
                setContentView(R.layout.activity_nueva_transferencia);
                movimiento = new Transferencia();
                movimientoService = new TransferenciaService();
                initSpinnerCuenta2(Cuenta.Moneda.PESOS);
                break;
            case COMPRA_DIVISA:
                setContentView(R.layout.activity_nueva_compra_divisa);
                movimiento = new CompraDivisa();
                movimientoService = new CompraDivisaService();
                monto2 = findViewById(R.id.et_montoDestino);
                initSpinnerCuenta2(Cuenta.Moneda.DOLARES);
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
                    validator.validarMonto(monto);
                    movimiento = movimientoService.mapearMovimiento(movimientoService.cargarMovimiento(getMapaDeElementos()));
                    movimientoService.guardarMovimiento(movimiento);
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
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones(Cuenta.Moneda.PESOS.getValue()));
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta.setAdapter(cuentaAdapter);
    }
    private void initSpinnerCuenta2(Cuenta.Moneda moneda) {
        spinnerCuenta2 = findViewById(R.id.spinnerCuenta2);
        ArrayAdapter<String> cuentaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones(moneda.getValue()));
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta2.setAdapter(cuentaAdapter);
    }
    private void initSpinnerPrestamo() {
        spinnerCuenta2 = findViewById(R.id.spinnerPrestamo);
        ArrayAdapter<String> prestamoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripcionesPrestamo());
        prestamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta2.setAdapter(prestamoAdapter);
    }

    private Map<String, Object> getMapaDeElementos() {
        Map<String, Object> elementos = new HashMap();
        elementos.put(MovimientoService.SPINNER_CATEG, spinnerCategoria);
        elementos.put(MovimientoService.SPINNER_CUENTA, spinnerCuenta);
        elementos.put(MovimientoService.SPINNER_CUENTA2, spinnerCuenta2);
        elementos.put(MovimientoService.DESCRIPCION, descGasto);
        elementos.put(MovimientoService.MONTO, monto);
        elementos.put(MovimientoService.MONTO2, monto2);
        return elementos;
    }

    private void initBotonAgregarPrestamo(){
        addPrestamo = findViewById(R.id.image_addPrestamo);
        addPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.APP_CONTEXT, NuevaCuentaActivity.class);
                i.putExtra("class", NuevoGastoActivity.class);
                i.putExtra(MovimientoService.PARAM_TIPO_MOVIMIENTO, tipoMovimiento);
                startActivity(i);
            }
        });
    }
}
