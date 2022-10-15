package com.example.tp4;

public class EArticulo {
    int Id;
    String Nombre;
    int Stock;
    int Id_Categoria;

    public EArticulo(int id, String nombre) {
        Id = id;
        Nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getId_Categoria() {
        return Id_Categoria;
    }

    public void setId_Categoria(int id_Categoria) {
        Id_Categoria = id_Categoria;
    }

    public int getIds(){
        String id = String.valueOf(Id);
        return id.hashCode();
    }




}
