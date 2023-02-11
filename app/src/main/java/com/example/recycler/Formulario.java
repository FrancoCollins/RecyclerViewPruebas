package com.example.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.recycler.entidad.Contacto;
import com.example.recycler.listaSingleton.ListaSingleton;

public class Formulario extends AppCompatActivity {

    private EditText nombre;
    private EditText compania;
    private Button aceptar;
    private Button cancelar;
    private Contacto contacto;
    private boolean editar;
    private Spinner spinner;
    private TextView titulo;
    private View mainLayout;


    private boolean comprobacionInicial() {
        Contacto contacto = (Contacto) getIntent().getSerializableExtra("SuperHeroe");
        if (contacto == null) {
            titulo = findViewById(R.id.textView);
            titulo.setText(R.string.crear_usuario);
            return false;
        } else
            this.contacto = contacto;
        return true;
    }

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
            Intent intent = new Intent(Formulario.this, Formulario.class);
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
        setContentView(R.layout.activity_second);
        spinner = findViewById(R.id.spinner);
        mainLayout = findViewById(R.id.layout_formulario);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // vacio
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
            nombre.setText(contacto.getNombre());
            compania.setText(contacto.getTelefono());
        }

        aceptar.setOnClickListener(view -> {
            if (nombre.getText().toString().length() > 0 && compania.getText().toString().length() > 0) {
                if (editar) {
                    contacto.setTelefono(String.valueOf(compania.getText()));
                    contacto.setNombre(String.valueOf(nombre.getText()));
                    switch (spinner.getSelectedItem().toString()) {
                        case "ROJO":
                            contacto.setColor(Color.RED);
                            break;
                        case "AZUL":
                            contacto.setColor(Color.BLUE);
                            break;
                        case "VERDE":
                            contacto.setColor(Color.GREEN);
                            break;
                        case "AMARILLO":
                            contacto.setColor(Color.YELLOW);
                            break;
                    }
                } else {
                    contacto = new Contacto();
                    contacto.setNombre(String.valueOf(nombre.getText()));
                    contacto.setTelefono(String.valueOf(compania.getText()));
                    switch (spinner.getSelectedItem().toString()) {
                        case "ROJO":
                            contacto.setColor(Color.RED);
                            break;
                        case "AZUL":
                            contacto.setColor(Color.BLUE);
                            break;
                        case "VERDE":
                            contacto.setColor(Color.GREEN);
                            break;
                        case "AMARILLO":
                            contacto.setColor(Color.YELLOW);
                            break;
                    }
                    ListaSingleton.getInstance().getListaSuperHeroes().add(contacto);
                    createNotificationChannel();
                    enviarNotificacion("Contacto creado exitosamente", "El contacto ha sido dado de alta correctamente");
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                }
            } else if (nombre.getText().toString().length() == 0) {
                createNotificationChannel();
                enviarNotificacion("Datos incompletos", "Por favor rellene el nombre del contacto");
            } else if (compania.getText().toString().length() == 0) {
                createNotificationChannel();
                enviarNotificacion("Datos incompletos", "Por favor rellene el numero del contacto");
            }
        });
        cancelar.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }

    private void createNotificationChannel() {
        //Preguntando si la version es superior a la 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1234", "CanalNotificacion", importance);
            channel.setDescription("Canal de prueba");
            // Registramos el canal en el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void enviarNotificacion(String titulo, String mensaje) {
        //Pedimos el canal creado anteriormente
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1234")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManagerCompact =
                NotificationManagerCompat.from(this);

        // Notificacion id deberia de ser unico por cada notificacion
        int notificationId = 1;
        //Enviakmos la notificacion
        notificationManagerCompact.notify(notificationId, builder.build());
    }

}