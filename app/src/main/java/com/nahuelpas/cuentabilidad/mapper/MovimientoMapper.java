package com.nahuelpas.cuentabilidad.mapper;

import com.nahuelpas.cuentabilidad.model.entities.Movimiento;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Cobranza;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.CompraDivisa;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Gasto;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Ingreso;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Prestamo;
import com.nahuelpas.cuentabilidad.model.entities.transacciones.Transferencia;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Ingreso mappearIngreso(Movimiento mov){
        Ingreso ingreso = new Ingreso();
        ingreso.setCodigo(mov.getCodigo());
        ingreso.setFecha(mov.getFecha());
        ingreso.setDescripcion(mov.getDescripcion());
        ingreso.setIdCuenta(mov.getIdCuenta());
        ingreso.setMonto(mov.getMonto());
        return ingreso;
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

    public CompraDivisa mappearCompraDivisa (Movimiento mov) {
        CompraDivisa compraDivisa = new CompraDivisa();
        compraDivisa.setCodigo(mov.getCodigo());
        compraDivisa.setFecha(mov.getFecha());
        compraDivisa.setDescripcion(mov.getDescripcion());
        compraDivisa.setIdCuenta(mov.getIdCuenta());
        compraDivisa.setMonto(mov.getMonto());
        compraDivisa.setIdCuentaDivisa(mov.getIdCuentaAlt());
        return compraDivisa;
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

    public String toAnioMes (Date fecha) {
        String a = new SimpleDateFormat("yyyy-MM").format(fecha);
        return a;
    }
}
