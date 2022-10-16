package com.example.tp4.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class ECategoria implements Serializable {
    private Integer  id;
    private String descripcion;

    public ECategoria(int id, String descripcion) {
        super();
        this.id = id;
        this.descripcion = descripcion;
    }

    public ECategoria() {
    }

    public Integer  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ECategoria)) return false;
        ECategoria that = (ECategoria) o;
        return getId().equals(that.getId()) && getDescripcion().equals(that.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescripcion());
    }
}
