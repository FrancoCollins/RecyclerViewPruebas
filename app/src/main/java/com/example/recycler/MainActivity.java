package com.example.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycler.adaptadores.AdaptadorSuperHeroePersonalizado;
import com.example.recycler.entidad.BuscadorContactos;
import com.example.recycler.entidad.Contacto;
import com.example.recycler.listaSingleton.ListaSingleton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView reyclerViewUser;
    private AdaptadorSuperHeroePersonalizado adaptadorUsuario;
    private Button botonSegunda;
    private View mainLayout;


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

    private void solicitarPermisoContactos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // Mostrar un mensaje explicando por qué se necesita el permiso
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        } else {
            // Ya tienes permiso para acceder a los contactos
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solicitarPermisoContactos();
        ListaSingleton.getInstance(getContentResolver());
        mainLayout = findViewById(R.id.mainLayout);

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
            finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        reyclerViewUser.getAdapter().notifyDataSetChanged();
    }
}