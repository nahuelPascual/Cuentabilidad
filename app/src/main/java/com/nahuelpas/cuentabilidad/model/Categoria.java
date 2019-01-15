package com.nahuelpas.cuentabilidad.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
