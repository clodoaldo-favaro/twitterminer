package com.example.twitterminer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PesquisaListViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewTituloPesquisa;
    private final ImageView imageViewDeletarPesquisa;

    public PesquisaListViewHolder(View view) {
        super(view);
        textViewTituloPesquisa = view.findViewById(R.id.textViewTituloPesquisa);
        imageViewDeletarPesquisa = view.findViewById(R.id.removePesquisa);
    }

    public ImageView getImageViewDeletarPesquisa() {
        return imageViewDeletarPesquisa;
    }

    public void setTitle (String text) { textViewTituloPesquisa.setText(text);}

    static PesquisaListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylclerview_pesquisa_row, parent, false);
        return new PesquisaListViewHolder(view);
    }
}
