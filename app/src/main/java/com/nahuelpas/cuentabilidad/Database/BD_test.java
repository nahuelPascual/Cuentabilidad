package com.nahuelpas.cuentabilidad.Database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.Categoria;
import com.nahuelpas.cuentabilidad.model.Cuenta;
import com.nahuelpas.cuentabilidad.model.Gasto;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BD_test extends AppCompatActivity {

    TextView tv_cantGastos, tv_cantCuentas, tv_cantCateg, tv_query;
    Button btn_cuentas, btn_categorias, btn_gastos;
    GastoDao_Impl gastoDao;
    CategoriaDao_Impl categoriaDao;
    CuentaDao_Impl cuentaDao;

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
    }

    private void imprimirGastos() {
        StringBuilder text = new StringBuilder();
        List<Gasto> gastos = gastoDao.getAll();
        if (gastos!=null) {
            for (Gasto gasto : gastos) {
                text.append(gasto.getCodigo() + " - ");
                text.append(gasto.getDescripcion() + " - $");
                text.append(gasto.getMonto() + " - ");
                text.append(new CategoriaDao_Impl(Database.getAppDatabase(this)).getById(gasto.getIdCategoria()).getDescripcion() + "\n");
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
