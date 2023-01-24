package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycler.entidad.SuperHeroe;
import com.example.recycler.listaSingleton.ListaSingleton;

public class Formulario extends AppCompatActivity {

    private EditText nombre;
    private EditText compania;
    private Button aceptar;
    private Button cancelar;
    private SuperHeroe superHeroe;
    private boolean editar;
    private Spinner spinner;

    private boolean comprobacionInicial() {
        SuperHeroe superHeroe = (SuperHeroe) getIntent().getSerializableExtra("SuperHeroe");
        if (superHeroe == null)
            return false;
        else
            this.superHeroe = superHeroe;
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        spinner = findViewById(R.id.spinner);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });
        cancelar = findViewById(R.id.cancelarFormulario);
        aceptar = findViewById(R.id.aceptarFormulario);
        compania = findViewById(R.id.compania);
        nombre = findViewById(R.id.nombre);
        editar = comprobacionInicial();
        if (editar) {
            nombre.setText(superHeroe.getNombre());
            compania.setText(superHeroe.getCompania());
        }

        aceptar.setOnClickListener(view -> {
            if (editar) {
                superHeroe.setCompania(String.valueOf(compania.getText()));
                superHeroe.setNombre(String.valueOf(nombre.getText()));
                switch (spinner.getSelectedItem().toString()) {
                    case "ROJO":
                        superHeroe.setColor(Color.RED);
                        break;
                    case "AZUL":
                        superHeroe.setColor(Color.BLUE);
                        break;
                    case "VERDE":
                        superHeroe.setColor(Color.GREEN);
                        break;
                    case "AMARILLO":
                        superHeroe.setColor(Color.YELLOW);
                        break;
                }
                Toast.makeText(this, "Superheroe con id; " + superHeroe.getId() + " editado correctamente", Toast.LENGTH_LONG).show();
            } else {
                superHeroe = new SuperHeroe();
                superHeroe.setNombre(String.valueOf(nombre.getText()));
                superHeroe.setCompania(String.valueOf(compania.getText()));
                switch (spinner.getSelectedItem().toString()) {
                    case "ROJO":
                        superHeroe.setColor(Color.RED);
                        break;
                    case "AZUL":
                        superHeroe.setColor(Color.BLUE);
                        break;
                    case "VERDE":
                        superHeroe.setColor(Color.GREEN);
                        break;
                    case "AMARILLO":
                        superHeroe.setColor(Color.YELLOW);
                        break;
                }
                ListaSingleton.getInstance().getListaSuperHeroes().add(superHeroe);
                Toast.makeText(this, "Superheroe con id " + superHeroe.getId() + "inscrito correcta", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );

            }
        });
        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "VOLVER", Toast.LENGTH_LONG).show();
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }
}