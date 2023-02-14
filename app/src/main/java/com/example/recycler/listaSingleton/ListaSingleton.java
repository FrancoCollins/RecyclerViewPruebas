package com.example.recycler.listaSingleton;

import android.content.ContentResolver;

import com.example.recycler.entidad.Videojuego;
import com.example.recycler.gestor.GestorVideojuego;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaSingleton {

    private static ListaSingleton instance;
    private List<Videojuego> listaSuperHeroes;
    public ContentResolver contentResolver;

    private ListaSingleton() {
        super();
        listaSuperHeroes = new ArrayList<>();
    }

    public static ListaSingleton getInstance() {
        if (instance == null) {
            instance = new ListaSingleton();
            Call<List<Videojuego>> aux = GestorVideojuego.getInstance().getGoRestUserApiService().getVideojuegos();
            aux.enqueue(new Callback<List<Videojuego>>() {
                @Override
                public void onResponse(Call<List<Videojuego>> call, Response<List<Videojuego>> response) {
                    List<Videojuego> aux = response.body();
                    for (Videojuego juego : aux) {
                        ListaSingleton.getInstance().getListaSuperHeroes().add(juego);
                    }
                }

                @Override
                public void onFailure(Call<List<Videojuego>> call, Throwable t) {

                }
            });

        }
        return instance;
    }

    public static ListaSingleton getInstance(ContentResolver contentResolver) {
        if (instance == null) {
            instance = new ListaSingleton();

        }
        return instance;
    }


    public List<Videojuego> getListaSuperHeroes() {
        return listaSuperHeroes;
    }

    public void borrar(Videojuego contacto) {
        listaSuperHeroes.remove(contacto);
    }
}