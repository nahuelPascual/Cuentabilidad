package com.nahuelpas.cuentabilidad.model.entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity
public class Categoria {

    public Categoria(Long codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    @PrimaryKey
    private Long codigo;
    private String descripcion;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
