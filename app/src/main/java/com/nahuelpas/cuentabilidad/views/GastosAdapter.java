package com.nahuelpas.cuentabilidad.views;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.DetalleGastoActivity;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.service.GastoService;

import java.text.DateFormat;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.MyViewHolder> {

    private List<Gasto> gastos ;
    private GastoService gastoService = new GastoService();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fecha, descripcion, valor, categoria, cuenta;

        public MyViewHolder(View view) {
            super(view);
            fecha = view.findViewById(R.id.fechaRow);
            descripcion = view.findViewById(R.id.descripcionRow);
            valor = view.findViewById(R.id.valorRow);
         //   categoria = (TextView) view.findViewById(R.id.categoriaRow);
         //   cuenta = (TextView) view.findViewById(R.id.cuentaRow);
        }
    }
    public GastosAdapter(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gasto_list_row, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetalleGastoActivity.class);
                i.putExtra(GastoService.PARAM_ID_GASTO, new Long(itemView.getId()));
                view.getContext().startActivity(i);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gasto gasto = gastos.get(position);
        holder.fecha.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()));
        holder.descripcion.setText(gasto.getDescripcion());
        holder.valor.setText("$ " + gastoService.formatearGasto(gasto.getMonto()));
        holder.itemView.setId(new Integer(gasto.getCodigo().toString()));
        setColores(gasto, holder);
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    private void setColores(Gasto gasto, MyViewHolder holder) {
        switch (gasto.getTipo()){
            case INGRESO:
                holder.fecha.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Ingreso));
                holder.descripcion.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Ingreso));
                holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Ingreso));
                break;
            case PRESTAMO:
                holder.fecha.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Prestamo));
                holder.descripcion.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Prestamo));
                holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Prestamo));
                break;
            case PAGO:
                holder.fecha.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Pago));
                holder.descripcion.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Pago));
                holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Tipo_Pago));
            case GASTO:
            default:
                break;
        }
    }
}
