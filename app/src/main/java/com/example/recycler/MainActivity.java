package com.example.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.recycler.adaptadores.AdaptadorSuperHeroePersonalizado;
import com.example.recycler.entidad.Videojuego;
import com.example.recycler.gestor.GestorVideojuego;
import com.example.recycler.listaSingleton.ListaSingleton;
import com.example.recycler.servicio.GoRestVideojuegoApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView reyclerViewUser;
    private AdaptadorSuperHeroePersonalizado adaptadorUsuario;
    private Button botonSegunda;
    private Button refrescar;
    private View mainLayout;
    private ProgressDialog mDefaultDialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_superior, menu);
        return true;
    }

    //Con este método detectaremos que opción del menú ha sido pulsada
    //el parametro MenuItem representa el objeto que fue seleccionado, no puede ser null
    //En el valor de retorno decimos si queremos procesar el elemento en este metodo o no
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_contact) {
            Intent intent = new Intent(MainActivity.this, Formulario.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        } else if (id == R.id.btn_dropdown) {
            item.setChecked(!item.isChecked());
            return true;
        } else if (id == R.id.rojo) {
            mainLayout.setBackgroundColor(Color.RED);
            return true;
        } else if (id == R.id.amarillo) {
            mainLayout.setBackgroundColor(Color.YELLOW);
            return true;
        } else if (id == R.id.azul) {
            mainLayout.setBackgroundColor(Color.BLUE);
            return true;
        } else if (id == R.id.verde) {
            mainLayout.setBackgroundColor(Color.GREEN);
            return true;
        } else if (id == R.id.salir_menu) {
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListaSingleton.getInstance(getContentResolver());
        GestorVideojuego.getInstance().inicializar();
        mainLayout = findViewById(R.id.mainLayout);
        reyclerViewUser = findViewById(R.id.rViewUsuario);
        botonSegunda = findViewById((R.id.segunda));
        refrescar = findViewById(R.id.refrescar);

        // Esta línea mejora el rendimiento, si sabemos que el contenido
        // no va a afectar al tamaño del RecyclerView
        reyclerViewUser.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        reyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        // Asociamos un adapter (ver más adelante cómo definirlo)
        List<Videojuego> listaVideojuegos = ListaSingleton.getInstance().getListaSuperHeroes();
        adaptadorUsuario = new AdaptadorSuperHeroePersonalizado(listaVideojuegos);
        Context context = getApplicationContext();
        adaptadorUsuario.setContext(context);
        reyclerViewUser.setAdapter(adaptadorUsuario);


        botonSegunda.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Formulario.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        reyclerViewUser.getAdapter().notifyDataSetChanged();
        refrescar.setOnClickListener(view -> {
            obtenerListaUsuariosRest();
            finish();
            startActivity(getIntent());
        });
    }


    public void obtenerListaUsuariosRest() {
        mostrarEspera();

        GoRestVideojuegoApiService goRestUsuarioApiService =
                GestorVideojuego.getInstance().getGoRestUserApiService();

        Call<List<Videojuego>> call = goRestUsuarioApiService.getVideojuegos();

        call.enqueue(new Callback<List<Videojuego>>() {

            @Override
            public void onResponse(Call<List<Videojuego>> call, Response<List<Videojuego>> response) {
                if (response.isSuccessful()) {
                    Log.d("Success", "Datos traidos del servicio");
                    //Gracias a Gson, me convierte los json a objetos Usuario
                    List<Videojuego> listaUsuarios = response.body();
                    for (Videojuego videojuego : listaUsuarios) {
                        System.out.println(videojuego.getNombre().toString());
                        ListaSingleton.getInstance().getListaSuperHeroes().add(videojuego);
                    }
                } else {
                    Log.d("Error", response.code() + " " + response.message());
                    return;
                }
                cancelarEspera();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Error", t.toString());
                cancelarEspera();
            }
        });
    }

    public void mostrarEspera() {
        mDefaultDialog = new ProgressDialog(this);
        // El valor predeterminado es la forma de círculos pequeños
        mDefaultDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        mDefaultDialog.setMessage("Solicitando datos ...");
        mDefaultDialog.setCanceledOnTouchOutside(false);// Por defecto true
        mDefaultDialog.show();
    }

    public void cancelarEspera() {
        mDefaultDialog.cancel();
    }
}