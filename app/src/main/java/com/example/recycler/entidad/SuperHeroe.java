package com.example.recycler.entidad;

import com.example.recycler.listaSingleton.ListaSingleton;

import java.io.Serializable;

public class SuperHeroe implements Serializable {
    private int id;
    private String nombre;
    private String compania;
    private int color;

    public SuperHeroe() {
        if (ListaSingleton.getInstance().getListaSuperHeroes() != null) {
            this.id = ListaSingleton.getInstance().getListaSuperHeroes().size();
        }
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


    public String getCompania() {
        return compania;
    }

    public void setCompania(String fechaNacimiento) {
        this.compania = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + compania + '\'' +
                '}';
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}