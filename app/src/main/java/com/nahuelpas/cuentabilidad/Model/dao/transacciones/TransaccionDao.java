package com.nahuelpas.cuentabilidad.Model.dao.transacciones;

import com.nahuelpas.cuentabilidad.Model.entities.transacciones.MovimientoBase;

public interface TransaccionDao <T extends MovimientoBase> {

    void eliminar (T transaccionDao);

    void guardar (T transaccionDao);

    T getById (Long id);


}
