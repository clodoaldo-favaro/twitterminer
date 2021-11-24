package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.viewmodel.PesquisaViewModel;

public class PesquisaDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";

    private PesquisaViewModel mPesquisaViewModel;
    private TextView textViewTituloPesquisa;
    private TextView textViewDescricaoPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_detalhe);

        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);
        textViewTituloPesquisa = findViewById(R.id.textViewPesquisaDetalheTitulo);
        textViewDescricaoPesquisa = findViewById(R.id.textViewPesquisaDetalheDescricao);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {
            int id = intent.getIntExtra(EXTRA_ID_PESQUISA, 0);

            mPesquisaViewModel.getPesquisaLiveDataById(id).observe(this, new Observer<Pesquisa>() {
                @Override
                public void onChanged(Pesquisa pesquisa) {
                    if (pesquisa != null) {
                        textViewTituloPesquisa.setText(pesquisa.getTitulo());
                        textViewDescricaoPesquisa.setText(pesquisa.getDescricao());
                    }
                }
            });
        }
    }
}