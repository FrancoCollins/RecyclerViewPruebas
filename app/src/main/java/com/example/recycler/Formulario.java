package com.example.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.recycler.entidad.Videojuego;
import com.example.recycler.gestor.GestorVideojuego;
import com.example.recycler.listaSingleton.ListaSingleton;
import com.example.recycler.servicio.GoRestVideojuegoApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Formulario extends AppCompatActivity {

    private EditText nombre;
    private EditText compania;
    private EditText nota;
    private Button aceptar;
    private Button cancelar;
    private Videojuego contacto;
    private boolean editar;
    private Spinner spinner;
    private TextView titulo;
    private View mainLayout;
    private ProgressDialog mDefaultDialog;

    private boolean comprobacionInicial() {
        Videojuego contacto = (Videojuego) getIntent().getSerializableExtra("SuperHeroe");
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
        nota = findViewById(R.id.nota_formulario);
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
            compania.setText(contacto.getCompania());
        }

        aceptar.setOnClickListener(view -> {
            if (nombre.getText().toString().length() > 0 && compania.getText().toString().length() > 0) {
                if (editar) {
                    System.out.println(contacto.getId() + "------------------------------------");
                    contacto.setCompania(String.valueOf(compania.getText()));
                    contacto.setNombre(String.valueOf(nombre.getText()));
                    contacto.setNota(Double.parseDouble(String.valueOf(nota.getText())));
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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    modificarVideojuegoREst(contacto);
                    createNotificationChannel();
                    enviarNotificacion("Contacto creado exitosamente", "El contacto ha sido dado de alta correctamente");
                    finish();
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                } else {
                    contacto = new Videojuego();
                    contacto.setNombre(String.valueOf(nombre.getText()));
                    contacto.setCompania(String.valueOf(compania.getText()));
                    contacto.setNota(Double.parseDouble(String.valueOf(nota.getText())));
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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    agregarVideojuegoREst(contacto);
                    createNotificationChannel();
                    enviarNotificacion("Contacto creado exitosamente", "El contacto ha sido dado de alta correctamente");
                    finish();
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

    public void modificarVideojuegoREst(Videojuego videojuego) {
        mostrarEspera();
        GoRestVideojuegoApiService goRestUsuarioApiService =
                GestorVideojuego.getInstance().getGoRestUserApiService();


        Call<Videojuego> call = goRestUsuarioApiService.modificarVideojuego(String.valueOf(videojuego.getId()), videojuego);
        call.enqueue(new Callback<Videojuego>() {
            @Override
            public void onResponse(Call<Videojuego> call, Response<Videojuego> response) {
                if (response.isSuccessful()) {
                    Videojuego p = response.body();
                    for (Videojuego juego : ListaSingleton.getInstance().getListaSuperHeroes()) {
                        if (juego.getId() == p.getId()) {
                            ListaSingleton.getInstance().borrar(juego);
                            ListaSingleton.getInstance().getListaSuperHeroes().add(p);
                        }
                    }
                } else {
                    Log.d("MAL", "ESTAMOS MAL");
                }
                cancelarEspera();
            }

            @Override
            public void onFailure(Call<Videojuego> call, Throwable t) {
                cancelarEspera();
            }
        });
        obtenerListaUsuariosRest();
    }

    public void agregarVideojuegoREst(Videojuego videojuego) {
        mostrarEspera();
        GoRestVideojuegoApiService goRestUsuarioApiService =
                GestorVideojuego.getInstance().getGoRestUserApiService();

        Call<Videojuego> call = goRestUsuarioApiService.crearVideojuego(videojuego);
        call.enqueue(new Callback<Videojuego>() {
            @Override
            public void onResponse(Call<Videojuego> call, Response<Videojuego> response) {
                if (response.isSuccessful()) {
                    Videojuego p = response.body();
                    ListaSingleton.getInstance().getListaSuperHeroes().add(p);
                    System.out.println(p.getId() + "-----------------------------");
                } else {
                    Log.d("MAL", "ESTAMOS MAL---------------------------");
                }
                cancelarEspera();
            }

            @Override
            public void onFailure(Call<Videojuego> call, Throwable t) {
                System.out.println("ERROOOORRRRRR");
                cancelarEspera();
            }
        });
    }

    public void mostrarEspera() {
        mDefaultDialog = new ProgressDialog(this);
        // El valor predeterminado es la forma de círculos pequeños
        mDefaultDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        mDefaultDialog.setMessage("Espere, estamos llevando a cabo su solicitud...");
        mDefaultDialog.setCanceledOnTouchOutside(false);// Por defecto true
        mDefaultDialog.show();
    }

    public void cancelarEspera() {
        mDefaultDialog.cancel();
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
                    ListaSingleton.getInstance().getListaSuperHeroes().clear();
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

}