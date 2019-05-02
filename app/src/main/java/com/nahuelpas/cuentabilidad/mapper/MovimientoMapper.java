package com.nahuelpas.cuentabilidad.mapper;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.MovimientoBase;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovimientoMapper {

    public Gasto mappearGasto(Movimiento mov){
        Gasto gasto = new Gasto();
        gasto.setCodigo(mov.getCodigo());
        gasto.setFecha(mov.getFecha());
        gasto.setDescripcion(mov.getDescripcion());
        gasto.setIdCuenta(mov.getIdCuenta());
        gasto.setMonto(mov.getMonto());
        gasto.setIdCategoria(mov.getIdCategoria());
        return gasto;
    }
    public List<Gasto> mappearGasto(List<Movimiento> movs){
        List<Gasto> gastos = new ArrayList<>();
        for (Movimiento mov : movs){
            gastos.add(mappearGasto(mov));
        }
        return gastos;
    }

    public Ingreso mappearIngreso(Movimiento mov){
        Ingreso ingreso = new Ingreso();
        ingreso.setCodigo(mov.getCodigo());
        ingreso.setFecha(mov.getFecha());
        ingreso.setDescripcion(mov.getDescripcion());
        ingreso.setIdCuenta(mov.getIdCuenta());
        ingreso.setMonto(mov.getMonto());
        return ingreso;
    }
    public List<Ingreso> mappearIngreso(List<Movimiento> movs){
        List<Ingreso> ingresos = new ArrayList<>();
        for (Movimiento mov : movs){
            ingresos.add(mappearIngreso(mov));
        }
        return ingresos;
    }

    public Prestamo mappearPrestamo(Movimiento mov) {
        Prestamo prestamo = new Prestamo();
        prestamo.setCodigo(mov.getCodigo());
        prestamo.setFecha(mov.getFecha());
        prestamo.setDescripcion(mov.getDescripcion());
        prestamo.setIdCuenta(mov.getIdCuenta());
        prestamo.setMonto(mov.getMonto());
        prestamo.setIdCuentaPrestamo(mov.getIdCuentaAlt());
        return prestamo;
    }
    public List<Prestamo> mappearPrestamo(List<Movimiento> movs){
        List<Prestamo> prestamos = new ArrayList<>();
        for (Movimiento mov : movs){
            prestamos.add(mappearPrestamo(mov));
        }
        return prestamos;
    }

    public Cobranza mappearCobranza(Movimiento mov) {
        Cobranza cobranza = new Cobranza();
        cobranza.setCodigo(mov.getCodigo());
        cobranza.setFecha(mov.getFecha());
        cobranza.setDescripcion(mov.getDescripcion());
        cobranza.setIdCuenta(mov.getIdCuenta());
        cobranza.setMonto(mov.getMonto());
        cobranza.setIdCuentaPrestamo(mov.getIdCuentaAlt());
        return cobranza;
    }
    public List<Cobranza> mappearCobranza(List<Movimiento> movs){
        List<Cobranza> cobranzas = new ArrayList<>();
        for (Movimiento mov : movs){
            cobranzas.add(mappearCobranza(mov));
        }
        return cobranzas;
    }

    public CompraDivisa mappearCompraDivisa (Movimiento mov) {
        CompraDivisa compraDivisa = new CompraDivisa();
        compraDivisa.setCodigo(mov.getCodigo());
        compraDivisa.setFecha(mov.getFecha());
        compraDivisa.setDescripcion(mov.getDescripcion());
        compraDivisa.setIdCuenta(mov.getIdCuenta());
        compraDivisa.setMonto(mov.getMonto());
        compraDivisa.setIdCuentaDivisa(mov.getIdCuentaAlt());
        compraDivisa.setMontoDivisa(mov.getMontoAlt());
        return compraDivisa;
    }
    public List<CompraDivisa> mappearCompraDivisa(List<Movimiento> movs){
        List<CompraDivisa> comprasDivisas = new ArrayList<>();
        for (Movimiento mov : movs){
            comprasDivisas.add(mappearCompraDivisa(mov));
        }
        return comprasDivisas;
    }

    public Transferencia mappearTransferencia (Movimiento mov) {
        Transferencia transferencia = new Transferencia();
        transferencia.setCodigo(mov.getCodigo());
        transferencia.setFecha(mov.getFecha());
        transferencia.setDescripcion(mov.getDescripcion());
        transferencia.setIdCuenta(mov.getIdCuenta());
        transferencia.setMonto(mov.getMonto());
        transferencia.setCuentaTransferencia(mov.getIdCuentaAlt());
        return transferencia;
    }
    public List<Transferencia> mappearTransferencia(List<Movimiento> movs){
        List<Transferencia> transferencias = new ArrayList<>();
        for (Movimiento mov : movs){
            transferencias.add(mappearTransferencia(mov));
        }
        return transferencias;
    }

    public String toAnioMes (Date fecha) {
        String a = new SimpleDateFormat("yyyy-MM").format(fecha);
        return a;
    }

    public MovimientoBase mappearMovimientoBase (Movimiento movimiento) {
        MovimientoBase movimientoBase;
        switch (movimiento.getTipo()) {
            case GASTO:
                movimientoBase = mappearGasto(movimiento);
                break;
            case INGRESO:
                movimientoBase = mappearIngreso(movimiento);
                break;
            case PRESTAMO:
                movimientoBase = mappearPrestamo(movimiento);
                break;
            case COBRANZA:
                movimientoBase = mappearCobranza(movimiento);
                break;
            case TRANSFERENCIA:
                movimientoBase = mappearCobranza(movimiento);
                break;
            case COMPRA_DIVISA:
                movimientoBase = mappearCompraDivisa(movimiento);
                break;
            default:
                movimientoBase = null;
                break;
        }
        return movimientoBase;
    }
    public List<MovimientoBase> mappearMovimientos (List<Movimiento> movimientos) {
        List<MovimientoBase> movimientosBase = new ArrayList<>();
        for (Movimiento mov : movimientos) {
            movimientosBase.add(mappearMovimientoBase(mov));
        }
        return movimientosBase;
    }

//    public List<Movimiento> mappearMovimientos (List<MovimientoBase> movimientosBase) {
//        List<Movimiento> movimientoList = new ArrayList<>();
//        for (MovimientoBase movim : movimientosBase) {
//            movimientoList.add(new Movimiento(movim));
//        }
//    }
}
