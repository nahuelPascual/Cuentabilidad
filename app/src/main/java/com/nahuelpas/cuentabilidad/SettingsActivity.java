package com.nahuelpas.cuentabilidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button nuevaCuenta, nuevaCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nuevaCuenta = findViewById(R.id.btn_nueva_cuenta);
        nuevaCategoria = findViewById(R.id.btn_nueva_categoria);

        nuevaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NuevaCategoriaActivity.class));
            }
        });
        nuevaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NuevaCuentaActivity.class);
                i.putExtra("class", SettingsActivity.class);
                startActivity(i);
            }
        });
    }
}
