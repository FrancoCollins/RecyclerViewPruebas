package com.example.recycler.listaSingleton;

import com.example.recycler.entidad.SuperHeroe;

import java.util.ArrayList;
import java.util.List;

public class ListaSingleton {

    private static ListaSingleton instance;
    private List<SuperHeroe> listaSuperHeroes;

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


    public List<SuperHeroe> getListaSuperHeroes() {
        return listaSuperHeroes;
    }

    public void borrar(SuperHeroe superHeroe) {
        listaSuperHeroes.remove(superHeroe);
    }
}