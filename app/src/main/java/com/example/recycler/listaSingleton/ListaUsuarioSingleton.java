package com.example.recycler.listaSingleton;

import com.example.recycler.entidad.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarioSingleton {

    private static ListaUsuarioSingleton instance;
    private List<Usuario> listaSuperHeroes;
    private int contador = 1;

    private ListaUsuarioSingleton(){
        super();
    }

    public static ListaUsuarioSingleton getInstance() {
        if(instance == null){
            instance = new ListaUsuarioSingleton();
        }
        return instance;
    }

    public void inicializar(){
        listaSuperHeroes = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setId(contador++);
        usuario.setNombre("Tony Stark");
        usuario.setEdad(45);
        usuario.setPeso(89.6);
        usuario.setFechaNacimiento("19/02/1975");

        listaSuperHeroes.add(usuario);

        usuario = new Usuario();
        usuario.setId(contador++);
        usuario.setNombre("Natasha Romanoff");
        usuario.setEdad(33);
        usuario.setPeso(62.5);
        usuario.setFechaNacimiento("19/02/1995");

        listaSuperHeroes.add(usuario);
        System.out.println("#########################" + listaSuperHeroes);
    }

    public List<Usuario> getListaSuperHeroes() {
        return listaSuperHeroes;
    }

    public void borrar(Usuario usuario){
        listaSuperHeroes.remove(usuario);
    }
}