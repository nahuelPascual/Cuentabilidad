package com.nahuelpas.cuentabilidad.mapper;

import com.nahuelpas.cuentabilidad.model.dto.GastoDto;
import com.nahuelpas.cuentabilidad.model.entities.Gasto;

import java.util.ArrayList;
import java.util.List;

public class GastoMapper {

    public GastoDto map (Gasto gasto){
        GastoDto gastoDto = new GastoDto();
        //gastoDto.setFecha(DateFormat.getDateInstance(DateFormat.SHORT).format(gasto.getFecha()).toString());
        gastoDto.setDescripcion(gasto.getDescripcion());
        // gastoDto.setValor(String.format("%.2f",gasto.getMonto()));
        return gastoDto; // TODO no usar un Dto, sino directamente la entity necesito el ID y la categoría para el detalle y aplicar filtros
    }

    public List<GastoDto> map (List<Gasto> gastos){
        List<GastoDto> gastosDto = new ArrayList<>();
        for (Gasto gasto : gastos) {
            gastosDto.add(map(gasto));
        }
        return gastosDto;
    }
}
