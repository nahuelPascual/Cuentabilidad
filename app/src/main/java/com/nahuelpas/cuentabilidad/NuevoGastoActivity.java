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
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;
import com.nahuelpas.cuentabilidad.service.CuentaService;
import com.nahuelpas.cuentabilidad.service.GastoService;
import com.nahuelpas.cuentabilidad.service.IngresoService;
import com.nahuelpas.cuentabilidad.service.MovimientoService;
import com.nahuelpas.cuentabilidad.service.CobranzaService;
import com.nahuelpas.cuentabilidad.service.PrestamoService;
import com.nahuelpas.cuentabilidad.service.TransferenciaService;
import com.nahuelpas.cuentabilidad.validator.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoGastoActivity extends AppCompatActivity {

    private Movimiento.Tipo tipoMovimiento;
    private MovimientoBase movimiento;

    private GastoDao gastoDao;
    private CategoriaDao categoriaDao;
    private CuentaDao cuentaDao;

    private Spinner spinnerCategoria, spinnerCuenta, spinnerCuenta2, spinnerPrestamo;
    private EditText descGasto, monto, monto2;
    private Button btn_saveGasto;

    private Validator validator = new Validator();
    private MovimientoMapper gastoMapper = new MovimientoMapper();
    private CuentaService cuentaService = new CuentaService();

    private MovimientoService movimientoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tipoMovimiento = (Movimiento.Tipo) getIntent().getExtras().get(GastoService.PARAM_TIPO_GASTO);

        /* inicializacion de DAOs */
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));

        switch(tipoMovimiento){
            case GASTO:
                movimiento = new Gasto();
                movimientoService = new GastoService();
                setContentView(R.layout.activity_nuevo_gasto);
                initSpinnerCategoria();
                break;
            case INGRESO:
                movimiento = new Ingreso();
                movimientoService = new IngresoService();
                setContentView(R.layout.activity_nuevo_ingreso);
                break;
            case PRESTAMO:
                movimiento = new Prestamo();
                movimientoService = new PrestamoService();
                setContentView(R.layout.activity_nuevo_prestamo);
                initSpinnerPrestamo();
            case COBRANZA:
                movimiento = new Cobranza();
                movimientoService = new CobranzaService();
                setContentView(R.layout.activity_nuevo_prestamo);
                initSpinnerPrestamo();
                break;
            case TRANSFERENCIA:
                movimiento = new Transferencia();
                movimientoService = new TransferenciaService();
                setContentView(R.layout.activity_nuevo_transferencia);
                initSpinnerCuenta2();
                monto2 = findViewById(R.id.et_montoDestino);
                monto2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        // TODO esto era sólo para compra de divisa
                        monto2.setText(monto.getText());
                    }
                });
                break;
            case COMPRA_DIVISA:
                //movimiento = new CompraDivisa();
                //TODO compra divisa
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
                    cargarMovimiento();
                    actualizarsSaldo();
                    gastoDao.add(gasto);
                    if(tipoMovimiento.equals(Movimiento.Tipo.TRANSFERENCIA)) {
                        //TODO acomodar esto
                        Gasto ggasto = new Gasto();
                        ggasto.setCodigo(gastoDao.getNextId());
                        ggasto.setTipo(tipoMovimiento);
                        ggasto.setFecha(gasto.getFecha());
                        ggasto.setAnio_mes(gastoMapper.toAnioMes(gasto.getFecha()));
                        ggasto.setDescripcion(gasto.getDescripcion());
                        ggasto.setMonto(Double.valueOf(monto2.getText().toString()));
                        ggasto.setIdCuenta(cuentaDao.getCuentaByDesc((String)spinnerCuenta2.getSelectedItem()).getCodigo());
                        gastoDao.add(ggasto);
                    }
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
    private void initSpinnerCuenta2() {
        spinnerCuenta2 = findViewById(R.id.spinnerCuenta2);
        ArrayAdapter<String> cuentaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripciones());
        cuentaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuenta2.setAdapter(cuentaAdapter);
    }
    private void initSpinnerPrestamo() {
        spinnerPrestamo = findViewById(R.id.spinnerPrestamo);
        ArrayAdapter<String> prestamoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cuentaDao.getDescripcionesPrestamo());
        prestamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrestamo.setAdapter(prestamoAdapter);
    }

    private void cargarMovimiento(){
        long id = gastoDao.getNextId();
    }
    private  Map<String, Object> getMapaDeElementos() {
        Map<String, Object> elementos = new HashMap();
        elementos.put(movimientoService.SPINNER_CATEG, spinnerCategoria);
        elementos.put(movimientoService.SPINNER_CUENTA, spinnerCuenta);
        elementos.put(movimientoService.SPINNER_CUENTA2, spinnerCuenta2);
        elementos.put(movimientoService.SPINNER_PRESTAMO, spinnerPrestamo);
        elementos.put(movimientoService.DESCRIPCION, descGasto);
        elementos.put(movimientoService.MONTO, monto);
        elementos.put(movimientoService.MONTO2, monto2);
        elementos.put(movimientoService.ID_CODIGO, )
        return elementos;
    }

    private void actualizarsSaldo() throws ValidationException {
        //TODO mejorar esto
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
                break;
            case TRANSFERENCIA:
                Cuenta destino = cuentaDao.getCuentaByDesc((String)spinnerCuenta2.getSelectedItem());
                validator.validarOrigenDestino(cuenta, destino);
                cuentaService.actualizarSaldoTransferencia(gasto.getMonto(), Double.valueOf(monto2.getText().toString()), cuenta, destino);
                break;
        }
    }
}
