package com.example.twitterminer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterminer.entities.Pesquisa;

import java.util.List;

public class PesquisaListAdapter extends ListAdapter<Pesquisa, PesquisaListViewHolder> {

    private Context context;
    private List<Pesquisa> pesquisaList;
    private HandlePesquisaClick clickListener;

    public PesquisaListAdapter(@NonNull DiffUtil.ItemCallback<Pesquisa> diffCallback, HandlePesquisaClick handlePesquisaClick) {
        super(diffCallback);
        this.clickListener = handlePesquisaClick;
    }

    @Override
    public PesquisaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PesquisaListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PesquisaListViewHolder holder, int position) {
        Pesquisa current = getItem(position);
        holder.setTitle(current.getTitulo());

        ImageView imageViewDelete = holder.getImageViewDeletarPesquisa();

        if (imageViewDelete != null) {
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.removeItem(current);
                }
            });
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(current);
            }
        });
    }

    public interface HandlePesquisaClick {
        void itemClick(Pesquisa pesquisa);
        void removeItem(Pesquisa pesquisa);
    }

    static class PesquisaDiff extends DiffUtil.ItemCallback<Pesquisa> {

        @Override
        public boolean areItemsTheSame(@NonNull Pesquisa oldItem, @NonNull Pesquisa newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pesquisa oldItem, @NonNull Pesquisa newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo());
        }
    }
}
