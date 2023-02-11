package com.example.recycler.listaSingleton;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;

import com.example.recycler.entidad.BuscadorContactos;
import com.example.recycler.entidad.Contacto;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaSingleton {

    private static ListaSingleton instance;
    private List<Contacto> listaSuperHeroes;
    public ContentResolver contentResolver;

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
    public static ListaSingleton getInstance(ContentResolver contentResolver) {
        if (instance == null) {
            instance = new ListaSingleton();
            instance.cargarContactos(contentResolver);

        }
        return instance;
    }

    public void cargarContactos(ContentResolver contentResolver) {
        List<Contacto> contactos = BuscadorContactos.getContactos(contentResolver);
        for (Contacto contacto : contactos) {
            contacto.setId(ListaSingleton.getInstance().getListaSuperHeroes().size());
            ListaSingleton.getInstance().getListaSuperHeroes().add(contacto);
        }
    }


    public List<Contacto> getListaSuperHeroes() {
        return listaSuperHeroes;
    }

    public void borrar(Contacto contacto) {
        listaSuperHeroes.remove(contacto);
    }
}