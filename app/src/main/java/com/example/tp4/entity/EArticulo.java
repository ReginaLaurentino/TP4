package com.example.tp4.entity;


import java.util.Objects;

public class EArticulo {

    private int id;

    private String nombre;

    private int stock;

    private ECategoria categoria;

    public EArticulo() {
    }

    public EArticulo(int id, String nombre) {
        id = id;
        nombre = nombre;
    }

    public int getIds(){
        String id = String.valueOf(getId());
        return id.hashCode();
    }

    public EArticulo(int id, String nombre, int stock, ECategoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ECategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ECategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "EArticulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", categoria=" + categoria +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EArticulo)) return false;
        EArticulo eArticulo = (EArticulo) o;
        return getCategoria().equals(eArticulo.getCategoria());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoria());
    }
}
