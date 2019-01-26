package com.nahuelpas.cuentabilidad.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nahuelpas.cuentabilidad.R;
import com.nahuelpas.cuentabilidad.model.entities.Cuenta;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cuenta_list_row, parent, false);

        return new CuentasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CuentasAdapter.MyViewHolder holder, int position) {
        Cuenta cuenta = cuentas.get(position);
        holder.descripcion.setText(cuenta.getDescripcion());
        holder.saldo.setText("$ " + (gastoService.tieneDecimales(cuenta.getSaldo()) ?
                                        String.format("%.2f",cuenta.getSaldo()) : (int) cuenta.getSaldo()));
    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }
}
