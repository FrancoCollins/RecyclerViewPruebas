package com.example.recycler.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler.Formulario;
import com.example.recycler.R;
import com.example.recycler.entidad.Usuario;
import com.example.recycler.listaSingleton.ListaUsuarioSingleton;

import java.util.List;

public class AdaptadorUsuarioPersonalizado extends RecyclerView.Adapter<AdaptadorUsuarioPersonalizado.ViewHolder> {

   private List<Usuario> listaUsuarios;
   public static Context context;

   public AdaptadorUsuarioPersonalizado(List<Usuario> listaUsuarios) {
       this.listaUsuarios = listaUsuarios;
   }

    public void setContext(Context mainActivityClass) {
       this.context = mainActivityClass;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       private TextView id;
       private TextView nombre;
       private TextView edad;
       private TextView peso;
       private TextView fechaNacimiento;
       private Button botonEditar;
       private Button botonEliminar;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            id = v.findViewById(R.id.idUsuario);
            nombre = v.findViewById(R.id.nombreUsuario);
            edad = v.findViewById(R.id.edadUsuario);
            peso = v.findViewById(R.id.pesoUsuario);
            fechaNacimiento = v.findViewById(R.id.fechaUsuario);

            botonEditar = v.findViewById(R.id.btnEditarUsuario);
            botonEliminar = v.findViewById(R.id.btnEliminarUsuario);
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
       String sId = String.valueOf(listaUsuarios.get(position).getId());
       holder.id.setText(sId);
       holder.nombre.setText(listaUsuarios.get(position).getNombre());
       String sEdad = String.valueOf(listaUsuarios.get(position).getEdad());
       holder.edad.setText(sEdad);
       String sPeso = String.valueOf(listaUsuarios.get(position).getPeso());
       holder.peso.setText(sPeso);
       holder.fechaNacimiento.setText(listaUsuarios.get(position).getFechaNacimiento());

       holder.botonEditar.setOnClickListener(view -> {
           Toast.makeText(holder.id.getContext(), "Editando usuario " + sId, Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(context, Formulario.class);
           intent.putExtra("SuperHeroe", ListaUsuarioSingleton.getInstance().getListaSuperHeroes().get(position));
           context.startActivity(intent);
       });

       holder.botonEliminar.setOnClickListener(view -> {
           Toast.makeText(holder.id.getContext(), "Eliminando usuario " + sId, Toast.LENGTH_SHORT).show();
           ListaUsuarioSingleton.getInstance().borrar(listaUsuarios.get(position));
           notifyDataSetChanged();
       });
   }

   //será quien devuelva la cantidad de items que se encuentra en la lista
   @Override
   public int getItemCount() {
       return listaUsuarios.size();
   }

}