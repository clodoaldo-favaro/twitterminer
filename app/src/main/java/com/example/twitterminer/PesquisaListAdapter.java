package com.example.twitterminer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterminer.entities.Pesquisa;

import java.util.List;

public class PesquisaListAdapter extends RecyclerView.Adapter<PesquisaListAdapter.PesquisaListViewHolder> {

    private Context context;
    private List<Pesquisa> pesquisaList;
    private HandlePesquisaClick clickListener;

    public PesquisaListAdapter(Context context, HandlePesquisaClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setPesquisaList(List<Pesquisa> pesquisaList) {
        this.pesquisaList = pesquisaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PesquisaListAdapter.PesquisaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recylclerview_pesquisa_row, parent, false);
        return new PesquisaListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesquisaListAdapter.PesquisaListViewHolder holder, int position) {
        holder.textViewTituloPesquisa.setText(this.pesquisaList.get(position).titulo);

        holder.imageViewDeletarPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeItem(pesquisaList.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(pesquisaList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pesquisaList == null || pesquisaList.size() == 0) {
            return 0;
        } else {
            return pesquisaList.size();
        }
    }

    public class PesquisaListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTituloPesquisa;
        ImageView imageViewDeletarPesquisa;

        public PesquisaListViewHolder(View view) {
            super(view);
            textViewTituloPesquisa = view.findViewById(R.id.textViewTituloPesquisa);
            imageViewDeletarPesquisa = view.findViewById(R.id.removePesquisa);
        }
    }

    public interface HandlePesquisaClick {
        void itemClick(Pesquisa pesquisa);
        void removeItem(Pesquisa pesquisa);
    }
}
