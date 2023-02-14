package com.example.recycler.entidad;

import com.example.recycler.listaSingleton.ListaSingleton;

import java.io.Serializable;

public class Videojuego implements Serializable {
    private int id;
    private String nombre;
    private String compania;
    private double nota;
    private int color;

    public Videojuego() {
        if (ListaSingleton.getInstance().getListaSuperHeroes() != null) {
            this.id = ListaSingleton.getInstance().getListaSuperHeroes().size();
        }
    }

    public Videojuego(String name, String phone) {
        this.nombre = name;
        this.compania = phone;
        if (ListaSingleton.getInstance().getListaSuperHeroes() != null) {
            this.id = ListaSingleton.getInstance().getListaSuperHeroes().size();
        }
    }
    public Videojuego(String name, String phone, int nota) {
        this.nombre = name;
        this.compania = phone;
        this.nota = nota;
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}