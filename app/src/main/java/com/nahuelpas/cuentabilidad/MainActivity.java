package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nahuelpas.cuentabilidad.Database.BD_test;
import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.Categoria;
import com.nahuelpas.cuentabilidad.model.Cuenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NuevoGastoActivity.class));
            }
        });

        initCuentas();
        initCategorias();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.testBD_item) {
            startActivity(new Intent(getApplicationContext(), BD_test.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void initCuentas() {
        CuentaDao_Impl dao = new CuentaDao_Impl(Database.getAppDatabase(this));
        int cantCuentas = dao.getCantidadRegistros();
        if(cantCuentas == 0) {
            dao.add(new Cuenta(dao.getNextId(), "Billetera", new Double(1000)));
            dao.add(new Cuenta(dao.getNextId(), "Santander Rio", new Double(15000)));
            dao.add(new Cuenta(dao.getNextId(), "Guardado CPU", new Double(8000)));
        }
    }
    private void initCategorias() {
        CategoriaDao_Impl dao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        int cant = dao.getCantidadRegistros();
        if(cant == 0) {
            dao.add(new Categoria(dao.getNextId(), "Combustible"));
            dao.add(new Categoria(dao.getNextId(), "Facultad"));
            dao.add(new Categoria(dao.getNextId(), "Joda"));
        }
    }
}
