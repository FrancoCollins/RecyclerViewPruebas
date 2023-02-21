package com.example.recycler.servicio;

import com.example.recycler.entidad.Videojuego;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GoRestVideojuegoApiService {

    @GET("videojuegos")
    Call<List<Videojuego>> getVideojuegos();

    @GET("videojuegos/{id}")
    Call<Videojuego> getVideojuegoPorId(@Path("id") String id);

    @POST("videojuegos")
    Call<Videojuego> crearVideojuego(@Body Videojuego videojuego);

    @PUT("videojuegos/{id}")
    Call<Void> modificarVideojuego(@Path("id") String id, @Body Videojuego videojuego);


    @DELETE("videojuegos/{id}")
    Call<Videojuego> borrarVideojuego(@Path("id") int id);
}