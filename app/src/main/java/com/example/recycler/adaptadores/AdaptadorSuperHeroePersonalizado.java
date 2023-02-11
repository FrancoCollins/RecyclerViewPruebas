package com.example.recycler.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler.Formulario;
import com.example.recycler.R;
import com.example.recycler.entidad.Contacto;

import com.example.recycler.listaSingleton.ListaSingleton;

import java.util.List;

public class AdaptadorSuperHeroePersonalizado extends RecyclerView.Adapter<AdaptadorSuperHeroePersonalizado.ViewHolder> {

   private List<Contacto> listaContacto;
   public static Context context;

   public AdaptadorSuperHeroePersonalizado(List<Contacto> listaContacto) {
       this.listaContacto = listaContacto;
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
       String sId = String.valueOf(listaContacto.get(position).getId());
       holder.id.setText(sId);
       holder.nombre.setText(listaContacto.get(position).getNombre());
       holder.compania.setText(listaContacto.get(position).getTelefono());
       holder.background.setBackgroundColor(listaContacto.get(position).getColor());

       holder.botonEditar.setOnClickListener(view -> {
           Intent intent = new Intent(context, Formulario.class);
           intent.putExtra("SuperHeroe",ListaSingleton.getInstance().getListaSuperHeroes().get(position));
           context.startActivity(intent);
       });

       holder.botonEliminar.setOnClickListener(view -> {
           ListaSingleton.getInstance().borrar(listaContacto.get(position));
           notifyDataSetChanged();
       });
   }

   //será quien devuelva la cantidad de items que se encuentra en la lista
   @Override
   public int getItemCount() {
       return listaContacto.size();
   }

}