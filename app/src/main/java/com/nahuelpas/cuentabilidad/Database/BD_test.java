package com.nahuelpas.cuentabilidad.Database;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GenericDao;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class BD_test extends AppCompatActivity {

    TextView tv_cantGastos, tv_cantCuentas, tv_cantCateg, tv_query;
    Button btn_cuentas, btn_categorias, btn_gastos, btn_ejecutarQuery;
    GastoDao gastoDao;
    CategoriaDao categoriaDao;
    CuentaDao cuentaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_test);

        initDAOs();
        initElementos();
        initListeners();
    }

    private void initDAOs(){
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(this));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(this));
        gastoDao = new GastoDao_Impl(Database.getAppDatabase(this));
    }
    private void initElementos(){
        tv_cantCateg = findViewById(R.id.tv_cantCategorias);
        tv_cantCuentas = findViewById(R.id.tv_cantCuentas);
        tv_cantGastos = findViewById(R.id.tv_cantGastos);
        tv_query = findViewById(R.id.tv_query);
        btn_categorias = findViewById(R.id.btn_categorias);
        btn_cuentas = findViewById(R.id.btn_cuentas);
        btn_gastos = findViewById(R.id.btn_gastos);
        btn_ejecutarQuery = findViewById(R.id.btn_ejecutarQuery);

        tv_query.setMovementMethod(new ScrollingMovementMethod());

        int cant = gastoDao.getCantidadRegistros();
        tv_cantGastos.setText(String.valueOf(cant));
        cant = categoriaDao.getCantidadRegistros();
        tv_cantCateg.setText(String.valueOf(cant));
        cant = cuentaDao.getCantidadRegistros();
        tv_cantCuentas.setText(String.valueOf(cant));
    }
    private void initListeners(){
        btn_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimirCategorias();
            }
        });
        btn_cuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimirCuentas();
            }
        });
        btn_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimirGastos();
            }
        });
        btn_ejecutarQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String query = btn_ejecutarQuery.getText().toString();
                try {
                    gastoDao.ejecutarQuery(new SimpleSQLiteQuery("UPDATE GASTO SET descripcion = 'Sueldo Enero' " +
                            "where codigo = 1"));
                    gastoDao.ejecutarQuery(new SimpleSQLiteQuery("UPDATE GASTO SET idcuenta = 6 " +
                            "where codigo = 1"));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Se rompió la query, papu.", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void imprimirGastos() {
        StringBuilder text = new StringBuilder();
        List<Gasto> gastos = gastoDao.getAll();
        if (gastos!=null) {
            for (Gasto gasto : gastos) {
                if(gasto.getTipo().getValue()== Gasto.Tipo.GASTO.getValue()){
                    text.append("[" + DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()) + "] ");
                    text.append(gasto.getCodigo() + " - ");
                    text.append(gasto.getDescripcion() + " - $");
                    text.append(String.format("%.2f",gasto.getMonto()) + " - ");
                    text.append(new CategoriaDao_Impl(Database.getAppDatabase(this)).getById(gasto.getIdCategoria()).getDescripcion() + " - ");
                    text.append(gasto.getTipo() + " - ");
                    text.append(new CuentaDao_Impl(Database.getAppDatabase(this)).getById(gasto.getIdCuenta()).getDescripcion() + "\n");
                } else {
                    text.append("[" + DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()) + "] ");
                    text.append(gasto.getCodigo() + " - ");
                    text.append(gasto.getDescripcion() + " - $");
                    text.append(String.format("%.2f",gasto.getMonto()) + " \n");
                }
            }
            tv_query.setText(text.toString());
        }
    }
    private void imprimirCuentas() {
        StringBuilder text = new StringBuilder();
        List<Cuenta> cuentas = cuentaDao.getAll();
        if (cuentas!= null) {
            for (Cuenta cuenta : cuentas) {
                text.append(cuenta.getCodigo() + " - ");
                text.append(cuenta.getDescripcion() + " - ");
                text.append(cuenta.isDescubierto() + " - ");
                text.append(cuenta.isPrestamo() + " - ");
                text.append(cuenta.getSaldo() + " \n");
            }
            tv_query.setText(text.toString());
        }
    }
    private void imprimirCategorias() {
        StringBuilder text = new StringBuilder();
        List<Categoria> categorias = categoriaDao.getAll();
        if (categorias!=null) {
            for (Categoria cat : categorias) {
                text.append(cat.getCodigo() + " - ");
                text.append(cat.getDescripcion() + " \n");
            }
            tv_query.setText(text.toString());
        }
    }

}
