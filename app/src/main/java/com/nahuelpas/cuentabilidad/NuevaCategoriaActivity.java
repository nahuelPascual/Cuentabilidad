package com.nahuelpas.cuentabilidad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaCategoriaActivity extends AppCompatActivity {

    Button save;
    CategoriaDao categoriaDao;
    EditText nombreCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);

        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        nombreCategoria = findViewById(R.id.et_nombreCategoriaNueva);
        save = findViewById(R.id.btn_guardarCategoria);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO validar campos vacios
                categoriaDao.add(new Categoria(categoriaDao.getNextId(),
                        nombreCategoria.getText().toString()));
                Toast.makeText(getApplicationContext(), "Categoria guardada exitosamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
