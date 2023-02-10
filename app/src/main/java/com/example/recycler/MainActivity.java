package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.recycler.adaptadores.AdaptadorSuperHeroePersonalizado;
import com.example.recycler.entidad.Contacto;
import com.example.recycler.listaSingleton.ListaSingleton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView reyclerViewUser;
    private AdaptadorSuperHeroePersonalizado adaptadorUsuario;
    private Button botonSegunda;
    private View mainLayout;
    private Button salir;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salir = findViewById(R.id.salir);
        mainLayout = findViewById(R.id.mainLayout);
        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (spinner.getSelectedItem().toString()) {
                    case "ROJO":
                        mainLayout.setBackgroundColor(Color.RED);
                        break;
                    case "AZUL":
                        mainLayout.setBackgroundColor(Color.BLUE);
                        break;
                    case "VERDE":
                        mainLayout.setBackgroundColor(Color.GREEN);
                        break;
                    case "AMARILLO":
                        mainLayout.setBackgroundColor(Color.YELLOW);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });

        salir.setOnClickListener(view -> {
            System.exit(0);
        });
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