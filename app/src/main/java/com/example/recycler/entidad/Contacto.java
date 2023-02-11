package com.example.recycler.entidad;

import com.example.recycler.listaSingleton.ListaSingleton;

import java.io.Serializable;

public class Contacto implements Serializable {
    private int id;
    private String nombre;
    private String telefono;
    private int color;

    public Contacto() {
        if (ListaSingleton.getInstance().getListaSuperHeroes() != null) {
            this.id = ListaSingleton.getInstance().getListaSuperHeroes().size();
        }
    }

    public Contacto(String name, String phone) {
        this.nombre = name;
        this.telefono = phone;
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


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String fechaNacimiento) {
        this.telefono = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + telefono + '\'' +
                '}';
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}