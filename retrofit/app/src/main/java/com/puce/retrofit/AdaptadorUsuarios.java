package com.puce.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder>{

    Context context;
    List<User> listaUsuarios;

    public AdaptadorUsuarios(Context context, List<User> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_usuario, null, false);
        return new UsuarioViewHolder(vista);
    }



    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.tvName.setText(listaUsuarios.get(position).getName());
        holder.tvRecord.setText(listaUsuarios.get(position).getRecord());
        holder.tvProduct.setText(listaUsuarios.get(position).getProduct());
        holder.tvCant.setText(listaUsuarios.get(position).getCant());
        holder.tvPrize.setText(listaUsuarios.get(position).getPrize());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRecord, tvProduct, tvCant, tvPrize;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName =itemView.findViewById(R.id.tvName);
            tvRecord = itemView.findViewById(R.id.tvRecord);
            tvProduct = itemView.findViewById(R.id.tvProduct);
            tvCant = itemView.findViewById(R.id.tvCant);
            tvPrize = itemView.findViewById(R.id.tvPrize);

        }
    }
}
