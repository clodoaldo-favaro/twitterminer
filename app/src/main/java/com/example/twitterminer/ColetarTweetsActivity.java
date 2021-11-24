package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.twitterminer.entities.Pesquisa;

public class ColetarTweetsActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";
    public static final String EXTRA_PALAVRAS_CHAVE = "pesquisa.palavras_chave";
    public static final String EXTRA_RESPOSTAS = "pesquisa.respostas";

    private Pesquisa pesquisaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_tweets);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {

        }

        Button buttonParar = findViewById(R.id.buttonColetarTweets);

        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColetarTweetsActivity.this, PesquisaDetalheActivity.class);
                intent.putExtra(PesquisaDetalheActivity.EXTRA_ID_PESQUISA, EXTRA_ID_PESQUISA);
                startActivity(intent);
            }
        });
    }
}