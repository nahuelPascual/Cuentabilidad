package com.nahuelpas.cuentabilidad;

import com.nahuelpas.cuentabilidad.model.dao.transacciones.GastoDao;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;

import org.junit.Assert;
import org.junit.Test;

public class GastosTest {

//    private GastoDao gastoDao = new GastoDao();
    private Gasto gasto;

    @Test
    public void nuevoGastoTest(){
        gasto = new Gasto();
        Assert.assertNotNull(gasto.getTipo());
        Assert.assertNotNull(gasto.getFecha());
    }
}
