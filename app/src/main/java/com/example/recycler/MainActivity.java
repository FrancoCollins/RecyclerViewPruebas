package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recycler.adaptadores.AdaptadorSuperHeroePersonalizado;
import com.example.recycler.entidad.Contacto;
import com.example.recycler.listaSingleton.ListaSingleton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView reyclerViewUser;
    private AdaptadorSuperHeroePersonalizado adaptadorUsuario;
    private Button botonSegunda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reyclerViewUser = findViewById(R.id.rViewUsuario);
        botonSegunda = findViewById((R.id.segunda));

        // Esta línea mejora el rendimiento, si sabemos que el contenido
        // no va a afectar al tamaño del RecyclerView
        reyclerViewUser.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        reyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        // Asociamos un adapter (ver más adelante cómo definirlo)
        List<Contacto> listaSuperHeroes = ListaSingleton.getInstance().getListaSuperHeroes();
        adaptadorUsuario = new AdaptadorSuperHeroePersonalizado(listaSuperHeroes);
        Context context = getApplicationContext();
        adaptadorUsuario.setContext(context);
        reyclerViewUser.setAdapter(adaptadorUsuario);


        botonSegunda.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Formulario.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        reyclerViewUser.getAdapter().notifyDataSetChanged();
    }
}