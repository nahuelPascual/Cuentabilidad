package com.nahuelpas.cuentabilidad.model.entities;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity
public class Categoria {

    public Categoria(Long codigo, String descripcion/*, List<Categoria> subCategorias*/) {
        this.codigo = codigo;
        this.descripcion = descripcion;
//        this.subCategorias = subCategorias;
    }

    @PrimaryKey private Long codigo;
    private String descripcion;
//    @Relation(parentColumn = "codigo", entityColumn = "codigo")
//    private List<Categoria> subCategorias;


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

//    public List<Categoria> getSubCategorias() {
//        return subCategorias;
//    }
//    public void setSubCategorias(List<Categoria> subCategorias) {
//        this.subCategorias = subCategorias;
//    }
}
