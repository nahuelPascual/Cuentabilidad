package com.nahuelpas.cuentabilidad;

import com.nahuelpas.cuentabilidad.Model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.Controller.service.MovimientoService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovimientoTest {

    private List<Integer> idsInsertados = new ArrayList<>();
    MovimientoService movimientoService;
    Map<String, Object> elementos;

    @Before
    public void init(){
//        elementos.put(MovimientoService.SPINNER_CATEG, spinnerCategoria);
//        elementos.put(MovimientoService.SPINNER_CUENTA, spinnerCuenta);
//        elementos.put(MovimientoService.SPINNER_CUENTA2, spinnerCuenta2);
//        elementos.put(MovimientoService.DESCRIPCION, descGasto);
//        elementos.put(MovimientoService.MONTO, monto);
//        elementos.put(MovimientoService.MONTO2, monto2);
    }

    @Test
    public void nuevoGasto(){
        Gasto gasto = new Gasto();
        movimientoService = MovimientoService.getInstancia(gasto.getTipo());
//        movimientoService.cargarMovimiento()

    }

}
