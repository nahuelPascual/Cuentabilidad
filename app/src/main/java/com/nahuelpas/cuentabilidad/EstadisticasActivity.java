package com.nahuelpas.cuentabilidad;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao;
import com.nahuelpas.cuentabilidad.model.dao.CategoriaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EstadisticasActivity extends AppCompatActivity {

    private final String COMBO_TIPO = "TIPOS";
    private final String COMBO_MESES = "MESES";

    Spinner spinnerMes, spinnerTipo;
    TextView tv;
    GastoDao gastoDao;
    CuentaDao cuentaDao;
    CategoriaDao categoriaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        gastoDao = new GastoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));
        cuentaDao = new CuentaDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

        pruebaTransferencias();
      //  pruebaAddView();

        initSpinnerCategorias();
        initSpinnerMeses();
        mostrarFiltrado();
    }

    private void initSpinnerCategorias(){
        List<String> descripciones = new ArrayList<>();
        descripciones.add(COMBO_TIPO);
        descripciones.add(Gasto.Tipo.GASTO.toString());
        descripciones.add(Gasto.Tipo.INGRESO.toString());
        descripciones.add(Gasto.Tipo.PRESTAMO.toString());
        descripciones.add(Gasto.Tipo.PAGO.toString());
        descripciones.add(Gasto.Tipo.TRANSFERENCIA.toString());

        spinnerTipo = findViewById(R.id.spinnerTipo);
        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, descripciones);
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(categoriasAdapter);
    }
    private void initSpinnerMeses(){
        List<String> meses = new ArrayList<>();
        List<Gasto> nulos = new ArrayList<>();
        meses.add(COMBO_MESES);
        nulos = gastoDao.getMesesNulos();
        meses.addAll(gastoDao.getMesesExistentes());
        spinnerMes = findViewById(R.id.spinnerFecha);
        ArrayAdapter<String> mesesAdapter = new ArrayAdapter<String>(MainActivity.APP_CONTEXT,
                android.R.layout.simple_spinner_item, meses);
        mesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(mesesAdapter);
    }

    private void mostrarFiltrado() {
        String mes = !COMBO_MESES.equals((String)spinnerMes.getSelectedItem())
                ? (String) spinnerMes.getSelectedItem()
                : null;
        List<Integer> tipos = new ArrayList<>();
        tipos.add(Gasto.Tipo.valueOf((String)spinnerTipo.getSelectedItem()).getValue());

        List<Gasto> gastos = gastoDao.getByFiltros(tipos, mes, null);
        StringBuilder sb = new StringBuilder();

        for(Gasto g : gastos) {

        }

        tv.setText(sb.toString());
    }

    private void pruebaAddView(){
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayoutEstadistica);
        Button button = new Button(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.tvEstadisticas);
        button.setLayoutParams(params);
        button.setText("Boton de Prueba");
        rl.addView(button);
    }

    private void pruebaTransferencias(){
        tv = findViewById(R.id.tvEstadisticas);
        tv.setMovementMethod(new ScrollingMovementMethod());
        List<Integer> tipos = new ArrayList<>();

        tipos.add(Gasto.Tipo.TRANSFERENCIA.getValue());
        StringBuilder sb = new StringBuilder("TRANSFERENCIAS\n");
        for (Gasto g : gastoDao.getByFiltros(tipos, null, null)){
            sb.append(g.getCodigo() + " - ");
            sb.append(g.getFecha() + " - ");
            sb.append(cuentaDao.getById(g.getIdCuenta()).getDescripcion() + " - ");
            sb.append(g.getMonto() + "\n");
        }
        tipos.clear();

        tipos.add(Gasto.Tipo.PRESTAMO.getValue());
        sb.append("\nPRESTAMOS\n");
        for (Gasto g : gastoDao.getByFiltros(tipos, null, null)){
            sb.append(g.getCodigo() + " - ");
            sb.append(g.getDescripcion() + " - ");
            sb.append(cuentaDao.getById(g.getIdCuenta()).getDescripcion() + " - ");
            sb.append(g.getMonto() + "\n");
        }
        tipos.clear();

        tipos.add(Gasto.Tipo.PAGO.getValue());
        sb.append("\nPAGOS\n");
        for (Gasto g : gastoDao.getByFiltros(tipos, null, null)){
            sb.append(g.getCodigo() + " - ");
            sb.append(g.getDescripcion() + " - ");
            sb.append(cuentaDao.getById(g.getIdCuenta()).getDescripcion() + " - ");
            sb.append(g.getMonto() + "\n");
        }

        tv.setText(sb.toString());
    }
}
