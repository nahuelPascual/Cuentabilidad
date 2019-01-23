package com.nahuelpas.cuentabilidad.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.dto.GastoDto;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.MyViewHolder> {

    List<Gasto> gastos ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fecha, descripcion, valor;

        public MyViewHolder(View view) {
            super(view);
            fecha = (TextView) view.findViewById(R.id.fechaRow);
            descripcion = (TextView) view.findViewById(R.id.descripcionRow);
            valor = (TextView) view.findViewById(R.id.valorRow);
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
        holder.fecha.setText(gasto.getFecha().toString());
        holder.descripcion.setText(gasto.getDescripcion());
        holder.valor.setText("$ " + gasto.getMonto());
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }
}
