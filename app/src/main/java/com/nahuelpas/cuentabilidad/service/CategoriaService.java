package com.nahuelpas.cuentabilidad.service;

import com.nahuelpas.cuentabilidad.Database.Database;
import com.nahuelpas.cuentabilidad.MainActivity;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.dao.GastoDao_Impl;
import com.nahuelpas.cuentabilidad.model.entities.Categoria;

public class CategoriaService {
/*
    GastoDao gastoDao = new GastoDao_Impl(Database.getAppDatabase(MainActivity.APP_CONTEXT));

    public double totalGastos(Categoria categoria) {
        if(categoria.getSubcategorias().isEmpty()){
            return totalGastosCategoria(categoria);
        } else {
            double total = 0;
            for (Categoria cat : categoria.getSubcategorias()) {
                total += totalGastos(cat);
            }
            return total;
        }
    }

    private double totalGastosCategoria(Categoria categoria) {
        double total = 0;
        for(double parcial : gastoDao.getMontosByCategoria(categoria.getCodigo())){
            total += parcial;
        }
        return total;
    }
    */
}
