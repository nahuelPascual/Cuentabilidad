package com.nahuelpas.cuentabilidad.views;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.DetalleCuentaActivity;
import com.nahuelpas.cuentabilidad.DetalleGastoActivity;
import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
import com.nahuelpas.cuentabilidad.service.CuentaService;
import com.nahuelpas.cuentabilidad.service.GastoService;

import java.text.DateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CuentasAdapter extends RecyclerView.Adapter<CuentasAdapter.MyViewHolder> {

    List<Cuenta> cuentas ;
    GastoService gastoService = new GastoService();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView descripcion, saldo;

        public MyViewHolder(View view) {
            super(view);
            descripcion = (TextView) view.findViewById(R.id.cuentaRow);
            saldo = (TextView) view.findViewById(R.id.saldoRow);
        }
    }
    public CuentasAdapter(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public CuentasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cuenta_list_row, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetalleCuentaActivity.class);
                i.putExtra(CuentaService.PARAM_ID_CUENTA, new Long(itemView.getId()));
                view.getContext().startActivity(i);
            }
        });
        return new CuentasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CuentasAdapter.MyViewHolder holder, int position) {
        Cuenta cuenta = cuentas.get(position);
        holder.descripcion.setText(cuenta.getDescripcion());
        holder.saldo.setText("$ " + (gastoService.formatearGasto(cuenta.getSaldo())));
        holder.itemView.setId(new Integer(cuenta.getCodigo().toString()));
        setColores(cuenta, holder);
    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }

    private void setColores(Cuenta cuenta, MyViewHolder holder) {
        if (cuenta.isPrestamo()) {
            holder.descripcion.setTextColor(Color.rgb(255,0,0));
            holder.saldo.setTextColor(Color.rgb(255,0,0));
        }
    }
}
