package com.nahuelpas.cuentabilidad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nahuelpas.cuentabilidad.Database.BD_test;
import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static Context APP_CONTEXT;

    private GastoDao gastoDao;
    private GastoService gastoService;
    private TextView gastosRow;

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

                final ArrayAdapter<Gasto.Tipo> arrayAdapter = new ArrayAdapter<Gasto.Tipo>(MainActivity.this, android.R.layout.simple_selectable_list_item);
                arrayAdapter.add(Gasto.Tipo.GASTO);
                arrayAdapter.add(Gasto.Tipo.INGRESO);
                arrayAdapter.add(Gasto.Tipo.PRESTAMO);
                arrayAdapter.add(Gasto.Tipo.PAGO);

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
                //startActivity(new Intent(getApplicationContext(), NuevoGastoActivity.class));
            }
        });

        initCuentas();
        initCategorias();
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(getApplicationContext()));
        gastoService = new GastoService();

        initRecyclerView();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCuentas() {
        CuentaDao dao = new CuentaDao_Impl(Database.getAppDatabase(this));
        int cantCuentas = dao.getCantidadRegistros();
        if(cantCuentas == 0) {
            dao.add(new Cuenta(dao.getNextId(), "Billetera", new Double(2275), false, false));
            dao.add(new Cuenta(dao.getNextId(), "Santander Rio", new Double(14295.97), false, false));
            dao.add(new Cuenta(dao.getNextId(), "Guardado CPU", new Double(11500), false, false));
            dao.add(new Cuenta(dao.getNextId(), "Prestamo Camila", new Double(2500), false, true));
        }
    }
    private void initCategorias() {
        CategoriaDao dao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        int cant = dao.getCantidadRegistros();
        if(cant == 0) {
            dao.add(new Categoria(dao.getNextId(), "Combustible"));
            dao.add(new Categoria(dao.getNextId(), "Facultad"));
            dao.add(new Categoria(dao.getNextId(), "Libreria"));
            dao.add(new Categoria(dao.getNextId(), "Joda"));
            dao.add(new Categoria(dao.getNextId(), "Farmacia"));

        }
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

    private void initRecyclerView(){
        final RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerGastos);

        mAdapter = new GastosAdapter(gastoDao.getAll());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        registerForContextMenu(recyclerView);
    }

}
