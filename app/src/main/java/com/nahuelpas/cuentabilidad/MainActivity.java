package com.nahuelpas.cuentabilidad;

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
import com.nahuelpas.cuentabilidad.service.GastoService;
import com.nahuelpas.cuentabilidad.views.GastosAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GastoDao gastoDao;
    private GastoService gastoService;
    private TextView gastosRow;

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
        gastoDao  = new GastoDao_Impl(Database.getAppDatabase(this));
        gastoService = new GastoService();
      /*  gastosRow = findViewById(R.id.descripcionRow);
        gastosRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });*/

        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerGastos);

        mAdapter = new GastosAdapter(gastoDao.getAll());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                /*
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);

                        Toast.makeText(MyActivity.this,"The Item Clicked is: "+ position ,Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
*/
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
        //prepareMovieData();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCuentas() {
        CuentaDao dao = new CuentaDao_Impl(Database.getAppDatabase(this));
        int cantCuentas = dao.getCantidadRegistros();
        if(cantCuentas == 0) {
            dao.add(new Cuenta(dao.getNextId(), "Billetera", new Double(1000)));
            dao.add(new Cuenta(dao.getNextId(), "Santander Rio", new Double(15000)));
            dao.add(new Cuenta(dao.getNextId(), "Guardado CPU", new Double(8000)));
        }
    }
    private void initCategorias() {
        CategoriaDao dao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        int cant = dao.getCantidadRegistros();
        if(cant == 0) {
            dao.add(new Categoria(dao.getNextId(), "Combustible"));
            dao.add(new Categoria(dao.getNextId(), "Facultad"));
            dao.add(new Categoria(dao.getNextId(), "Joda"));
        }
    }
}
