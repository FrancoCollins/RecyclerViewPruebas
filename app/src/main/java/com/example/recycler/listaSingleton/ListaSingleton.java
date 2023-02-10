package com.example.recycler.listaSingleton;

import com.example.recycler.entidad.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ListaSingleton {

    private static ListaSingleton instance;
    private List<Contacto> listaSuperHeroes;

    private ListaSingleton() {
        super();
        listaSuperHeroes = new ArrayList<>();
    }

    public static ListaSingleton getInstance() {
        if (instance == null) {
            instance = new ListaSingleton();
        }
        return instance;
    }


    public List<Contacto> getListaSuperHeroes() {
        return listaSuperHeroes;
    }

    public void borrar(Contacto contacto) {
        listaSuperHeroes.remove(contacto);
    }
}