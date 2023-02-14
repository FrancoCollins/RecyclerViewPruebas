package com.example.recycler.gestor;

import com.example.recycler.servicio.GoRestVideojuegoApiService;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestorVideojuego {

    private static GestorVideojuego instance = null;
    private GoRestVideojuegoApiService goRestUsuarioApiService = null;

    private GestorVideojuego(){

    }

    public static GestorVideojuego getInstance(){
        if(instance == null){
            instance = new GestorVideojuego();
        }
        return instance;
    }

    public void inicializar(){
        //Tenemos que configurar Retrofit para acceder al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.34.81.98:9432/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                )).build();

        //Establecemos la relacion entre el servicio y Retrofit
        goRestUsuarioApiService =
                retrofit.create(GoRestVideojuegoApiService.class);
    }

    public GoRestVideojuegoApiService getGoRestUserApiService(){
        return goRestUsuarioApiService;
    }
}