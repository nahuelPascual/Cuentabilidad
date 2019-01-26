package com.nahuelpas.cuentabilidad.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao;
import com.nahuelpas.cuentabilidad.model.dao.CuentaDao_Impl;
import com.nahuelpas.cuentabilidad.model.dto.GastoDto;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.service.GastoService;

import java.text.DateFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.MyViewHolder> {

    List<Gasto> gastos ;
    GastoService gastoService = new GastoService();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fecha, descripcion, valor, categoria, cuenta;

        public MyViewHolder(View view) {
            super(view);
            fecha = (TextView) view.findViewById(R.id.fechaRow);
            descripcion = (TextView) view.findViewById(R.id.descripcionRow);
            valor = (TextView) view.findViewById(R.id.valorRow);
       //     categoria = (TextView) view.findViewById(R.id.categoriaRow);
         //   cuenta = (TextView) view.findViewById(R.id.cuentaRow);
        }
    }
    public GastosAdapter(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gasto_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gasto gasto = gastos.get(position);
        holder.fecha.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()));
        holder.descripcion.setText(gasto.getDescripcion());
        holder.valor.setText("$ " + (!gastoService.tieneDecimales(gasto.getMonto()) ?
                                        String.format("%.0f",gasto.getMonto()) : String.format("%.2f",gasto.getMonto())) );
      //  holder.cuenta.setText(gasto.getIdCuenta());
      //  holder.categoria.setText(gasto.getIdCategoria());
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }
}
