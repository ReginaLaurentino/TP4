package com.example.tp4.entity;

public class ECategoria {
    private int id;
    private String descripcion;

    public ECategoria(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public ECategoria() {
    }

    public int getId() {
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
        return "ECategoria{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
