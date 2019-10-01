package com.nahuelpas.cuentabilidad.Model.dao;

import com.nahuelpas.cuentabilidad.Model.dao.transacciones.CobranzaDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.CompraDivisaDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.GastoDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.IngresoDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.PrestamoDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.TransaccionDao;
import com.nahuelpas.cuentabilidad.Model.dao.transacciones.TransferenciaDao;
import com.nahuelpas.cuentabilidad.Model.entities.Movimiento;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class MovimientoDao extends GenericDao<Movimiento> {

    private static final String TABLE_NAME = "Movimiento";

    public static TransaccionDao getInstancia(Movimiento.Tipo tipo) {
        switch (tipo) {
            case GASTO:
                return new GastoDao();
            case INGRESO:
                return new IngresoDao();
            case PRESTAMO:
                return new PrestamoDao();
            case COBRANZA:
                return new CobranzaDao();
            case TRANSFERENCIA:
                return new TransferenciaDao();
            case COMPRA_DIVISA:
                return new CompraDivisaDao();
            default:
                return null;
        }
    }

    private final String ORDER_BY_ANIO_MES_DESC = " ORDER BY anio_mes DESC";
    public static final int TIPO_GASTO = 0;
    public static final int TIPO_INGRESO = 1;
    public static final int TIPO_PRESTAMO = 2;
    public static final int TIPO_PAGO = 3;
    public static final int TIPO_TRANSFERENCIA = 4;
    public static final int TIPO_COMPRA_DIVISA = 5;

    public static final int TODOS_LOS_TIPOS = -1;

    @Override
    @Query ("SELECT * FROM " + TABLE_NAME + " WHERE codigo = :id")
    public abstract Movimiento getById(Long id);

    @Override
    @Query ("SELECT codigo FROM " + TABLE_NAME + ORDER_BY_CODIGO_DESC + " LIMIT 1")
    protected abstract int getUltimoId();

    @Override
    @Query ("SELECT Count(codigo) FROM " + TABLE_NAME)
    public abstract int getCantidadRegistros();

    @Query ("SELECT * FROM " + TABLE_NAME +
            " WHERE (:tipo = " + TODOS_LOS_TIPOS + " OR tipo = :tipo)"
            + ORDER_BY_CODIGO_DESC)
    public abstract List<Movimiento> getAll(int tipo);

    @Query ("SELECT * FROM " + TABLE_NAME +
            " WHERE idCategoria = :categoria" +
            " AND (:tipo = " + TODOS_LOS_TIPOS + " OR tipo = :tipo)"
            + ORDER_BY_CODIGO_DESC)
    public abstract List<Movimiento> getByCategoria(Long categoria, int tipo);

    @Query ("SELECT * FROM " + TABLE_NAME +
            " WHERE idCuenta = :cuenta" +
            " AND (:tipo = " + TODOS_LOS_TIPOS + " OR tipo = :tipo)"
            + ORDER_BY_CODIGO_DESC)
    public abstract List<Movimiento> getByCuenta(Long cuenta, int tipo);

    @Query ("SELECT * FROM " + TABLE_NAME +
            " WHERE anio_mes = :anioMes" +
            " AND (:tipo = " + TODOS_LOS_TIPOS + " OR tipo = :tipo)"
            + ORDER_BY_CODIGO_DESC)
    public abstract List<Movimiento> getByMes(String anioMes, int tipo);

    @Query ("SELECT DISTINCT anio_mes FROM " + TABLE_NAME +
            " WHERE (:tipo = " + TODOS_LOS_TIPOS + " OR tipo = :tipo)"
            + ORDER_BY_ANIO_MES_DESC)
    public abstract List<String> getMesesExistentes(int tipo);

    @Query("SELECT * FROM " + TABLE_NAME +
            " WHERE tipo IN (:tiposBuscados)" +
            " AND (:anio_mes IS NULL OR anio_mes = :anio_mes)" +
            " AND (:categoria IS NULL OR idCategoria = :categoria)"
            + ORDER_BY_CODIGO_DESC)
    public abstract List<Movimiento> getByFiltros(List<Integer> tiposBuscados, String anio_mes, Long categoria);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE anio_mes IS NULL")
    public abstract List<Movimiento> getMesesNulos();

    @Query("SELECT monto FROM " + TABLE_NAME)
    public abstract List<Double> getMontos();

    @Query("SELECT monto FROM " + TABLE_NAME + " WHERE idCategoria = :categoria")
    public abstract List<Double> getMontosByCategoria(Long categoria);

    @Query ("SELECT sum(monto) FROM " + TABLE_NAME +
            " WHERE tipo = " + TIPO_GASTO +
            " AND :idCategoria IS NULL OR :idCategoria = idCategoria")
    public abstract double getTotalCategoria(Long idCategoria);
}
