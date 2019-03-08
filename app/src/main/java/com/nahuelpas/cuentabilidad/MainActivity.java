package com.nahuelpas.cuentabilidad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nahuelpas.cuentabilidad.Database.BD_test;
import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.mapper.MovimientoMapper;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.service.GastoService;
import com.nahuelpas.cuentabilidad.views.GastosAdapter;

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

    private GastoDao gastoDao;
    private GastoService gastoService;
    private TextView totalGastos;
    private Spinner calculoGastos;
    private MovimientoMapper gastoMapper;

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
                        i.putExtra(GastoService.PARAM_TIPO_GASTO, arrayAdapter.getItem(which));
                        startActivity(i);
                    }
                });
                builderSingle.show();
            }
        });

        gastoDao = new GastoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        gastoMapper = new MovimientoMapper();
        gastoService = new GastoService();
        calculoGastos = findViewById(R.id.spinner_total_gastos);
        calculoGastos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //mapearAnioMes();
        totalGastos = findViewById(R.id.tv_totalGastos);
        initDatos();
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

    private void initRecyclerView(List<Movimiento> gastos){
        final RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;

        recyclerView = findViewById(R.id.recyclerGastos);

        mAdapter = new GastosAdapter(gastos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //TODO agregar long click para menú
        //registerForContextMenu(recyclerView);
    }

    private void initDatos(){
        List<Integer> tiposBuscados = new ArrayList<>();
        boolean abs = false;
        switch (calculoGastos.getSelectedItem().toString()) {
            case "Gastos":
                tiposBuscados.add(Movimiento.Tipo.GASTO.getValue());
                abs = true;
                break;
            case "Neto":
                break;
        }
        List<Movimiento> gastos = gastoDao.getAll();
                //gastoDao.getByFiltros(tiposBuscados, gastoMapper.toAnioMes(new Date()), null);
        totalGastos.setText("$" + gastoService.calcularTotal(gastos, abs));
        initRecyclerView(gastos);
    }
}
