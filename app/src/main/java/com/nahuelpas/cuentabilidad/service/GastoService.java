package com.nahuelpas.cuentabilidad.service;

import android.widget.Spinner;

import com.nahuelpas.cuentabilidad.model.dao.GastoDao;
import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GastoService extends MovimientoService {

    private CuentaService cuentaService = new CuentaService();
    private GastoDao gastoDao = new GastoDao();

    public final static String PARAM_ID_GASTO = "idGasto";
    public final static String PARAM_TIPO_GASTO = "tipoGasto";

    public int getPosicionItemSpinner(Spinner spinner, String descripcion) {
        for (int i=0; i<spinner.getCount(); i++){
            String item = (String) spinner.getItemAtPosition(i);
            if(item.equals(descripcion)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Movimiento cargarMovimiento(Map<String, Object> elementos) {
        Spinner spinnerCategoria = (Spinner) elementos.get(SPINNER_CATEG);
        Gasto gasto = new Gasto();
        cargarMovimiento(elementos, gasto);
        gasto.setIdCategoria(spinnerCategoria!=null? categoriaDao.getCategoriaByDesc(spinnerCategoria.getSelectedItem().toString()).getCodigo() :null);
        return new Movimiento(gasto);
    }

    public String formatearGasto(double gasto) {
        return !tieneDecimales(gasto) ?
                String.format("%.0f",gasto) : String.format("%.2f",gasto);
    }

    @Override
    public void guardarMovimiento(Movimiento movimiento) {

    }

    private boolean tieneDecimales (double montoGasto) {
        return montoGasto != (int) montoGasto;
    }

    public void eliminarMovimiento(Gasto gasto) {
        cuentaService.ingresarDinero(gasto.getMonto(), cuentaDao.getById(gasto.getIdCuenta()));
        gastoDao.eliminar(gasto);
    }
//    public void eliminarGasto(Movimiento gasto) throws ValidationException{
//        int modificador = getMultiplicadorGasto(gasto); //TODO sacar a clase particular overrideando metodos guardar, eliminar, etc
//
//        Cuenta cuenta = cuentaDao.getById(gasto.getIdCuenta());
//        cuentaService.actualizarSaldo(gasto.getMonto()*modificador, cuentaDao.getById(gasto.getIdCuenta()));
//        movimientoDao.delete(gasto);
//    }

    public int getMultiplicadorGasto(Movimiento gasto) {
        /* tipos de movimiento positivos deben restar al eliminarse */
        List<Integer> ingresos = new ArrayList<>();
        ingresos.add(Movimiento.Tipo.INGRESO.getValue());
        ingresos.add(Movimiento.Tipo.COBRANZA.getValue());

        //TODO parche
        if(Movimiento.Tipo.TRANSFERENCIA.equals(gasto.getTipo())) return 0;

        return ingresos.contains(gasto.getTipo().getValue()) ? (1) : (-1) ;
    }

    public double calcularTotal(List<Movimiento> gastos, boolean abs){
        double total = 0;

        for (Movimiento gasto : gastos) {
            int modificador = getMultiplicadorGasto(gasto);
            total += gasto.getMonto() * modificador;
        }
        return abs ? Math.abs(total) : total;
    }
/*
    public Categoria getCategoria (Gasto gasto) {
        categoriaDao = new CategoriaDao_Impl(Database.getAppDatabase(getApplicationContext()));
        return categoriaDao.getById(gasto.getIdCategoria());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
*/
}
