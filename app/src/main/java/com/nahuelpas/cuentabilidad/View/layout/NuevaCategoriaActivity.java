package com.nahuelpas.cuentabilidad.View.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.Model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.Model.entities.Categoria;
import com.nahuelpas.cuentabilidad.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaCategoriaActivity extends AppCompatActivity {

    Button save;
    CategoriaDao categoriaDao;
    EditText nombreCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);

        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        nombreCategoria = findViewById(R.id.et_nombreCategoriaNueva);
        save = findViewById(R.id.btn_guardarCategoriaNueva);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreCategoria.getText().toString();
                if (nombre!=null && !nombre.isEmpty()) {
                    categoriaDao.add(new Categoria(categoriaDao.getNextId(), nombre));
                    Toast.makeText(getApplicationContext(), "Categoria guardada exitosamente.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Revise los datos ingresados.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
