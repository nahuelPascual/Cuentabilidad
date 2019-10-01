package com.nahuelpas.cuentabilidad.View.layout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nahuelpas.cuentabilidad.Database.BD_test;
import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.Controller.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao;
import com.nahuelpas.cuentabilidad.Model.dao.MovimientoDao_Impl;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.GastoDao;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;
import com.nahuelpas.cuentabilidad.Controller.service.transacciones.GastoService;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.View.GastosAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context APP_CONTEXT;
    public static String FILTRO_MAIN_ACTIVITY = "filtro_main_activity";
//    Object a = getIntent();
//    Object b = getIntent().getExtras();
//    Object c = getIntent().getExtras().get(FILTRO_MAIN_ACTIVITY);
    private Filtro selectedFiltro = getIntent()!=null ? (Filtro) getIntent().getExtras().get(FILTRO_MAIN_ACTIVITY) : Filtro.NETO ;

    private MovimientoDao movimientoDao;
    private GastoDao gastoDao;
    private CuentaDao cuentaDao;
    private GastoService gastoService;
    private TextView totalGastos;
    private Spinner calculoGastos;
    private MovimientoMapper movimientoMapper;

    enum Filtro {
        NETO("Neto"),
        PRESTAMOS("Prestamos"),
        GASTOS("Gastos");
        String value;
        Filtro(String n){
            value=n;
        }
        public String getValue(){
            return value;
        }
        public static List<String> getMappedValues(){
            List<String> lista = new ArrayList<>();
            for(Filtro f : values()){
                lista.add(f.getValue());
            }
            return lista;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        APP_CONTEXT = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                builderSingle.setTitle("Agregar nuevo");

                final ArrayAdapter<Movimiento.Tipo> arrayAdapter = new ArrayAdapter<Movimiento.Tipo>(MainActivity.this, android.R.layout.simple_selectable_list_item);
                arrayAdapter.add(Movimiento.Tipo.GASTO);
                arrayAdapter.add(Movimiento.Tipo.INGRESO);
                arrayAdapter.add(Movimiento.Tipo.PRESTAMO);
                arrayAdapter.add(Movimiento.Tipo.COBRANZA);
                arrayAdapter.add(Movimiento.Tipo.TRANSFERENCIA);
                arrayAdapter.add(Movimiento.Tipo.COMPRA_DIVISA);

                builderSingle.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, NuevoGastoActivity.class);
                        i.putExtra(MovimientoService.PARAM_TIPO_MOVIMIENTO, arrayAdapter.getItem(which));
                        i.putExtra(FILTRO_MAIN_ACTIVITY, selectedFiltro);
                        startActivity(i);
                    }
                });
                builderSingle.show();
            }
        });

        movimientoDao = new MovimientoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        gastoDao = new GastoDao();
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        movimientoMapper = new MovimientoMapper();
        gastoService = new GastoService();
        calculoGastos = findViewById(R.id.spinner_total_gastos);
        initSpinnerFiltros();
        calculoGastos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFiltro = (Filtro) calculoGastos.getSelectedItem();
                initDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        totalGastos = findViewById(R.id.tv_totalGastos);
        initDatos();
        getSharedPreferences("MainActivity", MODE_PRIVATE).edit().putString(FILTRO_MAIN_ACTIVITY, selectedFiltro.getValue());

//        populateBD();
//        queriesMagicas();

    }

    private void initSpinnerFiltros(){
        calculoGastos = findViewById(R.id.spinner_total_gastos);
        ArrayAdapter<Filtro> adapter = new ArrayAdapter<Filtro>(this,
                android.R.layout.simple_spinner_item, Filtro.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calculoGastos.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.testBD_item:
                startActivity(new Intent(getApplicationContext(), BD_test.class));
                break;
            case R.id.cuentas_menu_item:
                startActivity(new Intent(getApplicationContext(), CuentasActivity.class));
                break;
            case R.id.estadisticas_menu_item:
                startActivity(new Intent(getApplicationContext(), EstadisticasActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cuentas_menu_item:
                Toast.makeText(this, "Cuentas", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initRecyclerView(List<Movimiento> movimientos){
        final RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;

        recyclerView = findViewById(R.id.recyclerGastos);

        mAdapter = new GastosAdapter(movimientos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //TODO agregar long click para menú
        //registerForContextMenu(recyclerView);
    }

    private void initDatos(){
        List movimientos = new ArrayList();
        double total = 0;
        switch ((Filtro) calculoGastos.getSelectedItem()) {
            case GASTOS:
//                movimientos = gastoDao.getAll();
                movimientos = movimientoDao.getAll(MovimientoDao.TIPO_GASTO);
                total = gastoDao.getTotal();
                break;
            case NETO:
                movimientos = movimientoDao.getAll(MovimientoDao.TODOS_LOS_TIPOS);
                total = gastoService.calcularTotal(movimientos);
                break;
            case PRESTAMOS:
                movimientos = movimientoDao.getAll(MovimientoDao.TIPO_PRESTAMO);
                total = gastoService.calcularTotal(movimientos);
                break;
        }
        totalGastos.setText("$" + total);
        initRecyclerView(movimientos);
    }

    private void queriesMagicas() {
//        CuentaDao cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(getApplicationContext()));
//        PrestamoDao prestamoDao = new PrestamoDao();
//        Cuenta cuenta;
//        CompraDivisaDao cdDao = new CompraDivisaDao();
//        String msg;
//
//        //USD
//        cuenta = cuentaDao.getById(5L);
//        cuenta.setSaldo(cuenta.getSaldo()+200);
//        CompraDivisa cd = cdDao.getById(2L);
//        cd.setDescripcion("Compra USD");
//        cd.setMontoDivisa(200);
//        cuentaDao.update(cuenta);
//        cdDao.update(cd);
//        msg = msg + cd.getDescripcion() + " - " + cuenta.getDescripcion() + "\n";
//
//        Toast.makeText(APP_CONTEXT, msg, Toast.LENGTH_LONG).show();
    }
}
