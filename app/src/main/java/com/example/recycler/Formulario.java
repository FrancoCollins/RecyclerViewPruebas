package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.recycler.adaptadores.AdaptadorUsuarioPersonalizado;
import com.example.recycler.entidad.Usuario;
import com.example.recycler.listaSingleton.ListaUsuarioSingleton;

import java.util.List;

public class Formulario extends AppCompatActivity {

    private RecyclerView recyclerViewUser;
    private AdaptadorUsuarioPersonalizado adaptadorUsuario;
    private Button aceptar;
    private Button volver;

    private boolean comprobarEditar(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Usuario superHeroe = (Usuario) intent.getSerializableExtra("SuperHeroe");
        if (superHeroe != null){
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        aceptar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Usuario superHeroe = (Usuario) intent.getSerializableExtra("SuperHeroe");
            if (superHeroe != null){
                superHeroe.setEdad(1);
                superHeroe.setFechaNacimiento("pepe");
                superHeroe.setNombre("pepe");
                superHeroe.setPeso(1);

            }else {
                superHeroe = new Usuario();
                ListaUsuarioSingleton.getInstance().getListaSuperHeroes().add(superHeroe);
            }
        });
    }
}