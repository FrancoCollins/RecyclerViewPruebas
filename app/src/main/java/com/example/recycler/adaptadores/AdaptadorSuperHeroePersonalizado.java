package com.example.recycler.adaptadores;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler.Formulario;
import com.example.recycler.MainActivity;
import com.example.recycler.R;
import com.example.recycler.entidad.Videojuego;

import com.example.recycler.gestor.GestorVideojuego;
import com.example.recycler.listaSingleton.ListaSingleton;
import com.example.recycler.servicio.GoRestVideojuegoApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorSuperHeroePersonalizado extends RecyclerView.Adapter<AdaptadorSuperHeroePersonalizado.ViewHolder> {

    private List<Videojuego> listaVideojuego;
    public static Context context;
    private ProgressDialog mDefaultDialog;

    public AdaptadorSuperHeroePersonalizado(List<Videojuego> listaVideojuego) {
        this.listaVideojuego = listaVideojuego;
    }

    public void setContext(Context mainActivityClass) {
        this.context = mainActivityClass;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView nombre;
        private TextView compania;
        private Button botonEditar;
        private Button botonEliminar;
        private LinearLayout background;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            id = v.findViewById(R.id.idUsuario);
            nombre = v.findViewById(R.id.nombreUsuario);
            compania = v.findViewById(R.id.companiaUsuario);
            botonEditar = v.findViewById(R.id.btnEditarUsuario);
            botonEliminar = v.findViewById(R.id.btnEliminarUsuario);
            background = v.findViewById(R.id.linearLayout);
        }
    }

    //será quien devuelva el ViewHolder con el layout seteado que previamente definimos
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //será quien se encargue de establecer los objetos en el ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String sId = String.valueOf(listaVideojuego.get(position).getId());
        holder.id.setText(sId);
        holder.nombre.setText(listaVideojuego.get(position).getNombre());
        holder.compania.setText(listaVideojuego.get(position).getCompania());
        holder.background.setBackgroundColor(listaVideojuego.get(position).getColor());

        holder.botonEditar.setOnClickListener(view -> {
            Intent intent = new Intent(context, Formulario.class);
            System.out.println(sId + "-------------------------------------------------");
            intent.putExtra("SuperHeroe", ListaSingleton.getInstance().getListaSuperHeroes().get(position));
            System.out.println(ListaSingleton.getInstance().getListaSuperHeroes().get(position).getId() + "-------------------------------------");
            context.startActivity(intent);

        });

        holder.botonEliminar.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            borrarVideojuegoRest(Integer.parseInt(holder.id.getText().toString()));
            notifyDataSetChanged();
            context.startActivity(intent);

        });
    }

    public void borrarVideojuegoRest(int id) {
        mostrarEspera();

        GoRestVideojuegoApiService goRestUsuarioApiService =
                GestorVideojuego.getInstance().getGoRestUserApiService();

        Call<Videojuego> call = goRestUsuarioApiService.borrarVideojuego(id);

        call.enqueue(new Callback<Videojuego>() {
            @Override
            public void onResponse(Call<Videojuego> call, Response<Videojuego> response) {
                if (response.isSuccessful()) {
                    Videojuego p = response.body();
                    ListaSingleton.getInstance().borrar(p);
                    System.out.println(p);
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

    public void mostrarEspera() {
        mDefaultDialog = new ProgressDialog(context);
        // El valor predeterminado es la forma de círculos pequeños
        mDefaultDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        mDefaultDialog.setMessage("Espere, estamos llevando a cabo su solicitud...");
        mDefaultDialog.setCanceledOnTouchOutside(false);// Por defecto true
        mDefaultDialog.show();
    }

    public void cancelarEspera() {
        mDefaultDialog.cancel();
    }

    //será quien devuelva la cantidad de items que se encuentra en la lista
    @Override
    public int getItemCount() {
        return listaVideojuego.size();
    }

}