package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.twitterminer.entities.Pesquisa;

import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class ColetarTweetsActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";
    public static final String EXTRA_PALAVRAS_CHAVE = "pesquisa.palavras_chave";
    public static final String EXTRA_RESPOSTAS = "pesquisa.respostas";

    private Pesquisa pesquisaTela;
    private int idPesquisa;
    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_tweets);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {
            idPesquisa = intent.getIntExtra(EXTRA_ID_PESQUISA, 0);
            twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer("gbeDAH1NxF35GJmDQLBPNE0d4", "mE8kfbyxbd1e2ecIFWcxHlIf4tl3hrCA2RvMIPh2QHpTWtYEAu");

            String palavrasChave = intent.getStringExtra(EXTRA_PALAVRAS_CHAVE);
            String[] arrayPalavras = palavrasChave.split(",");
            String queryString = "";
            for (int i = 0; i < arrayPalavras.length; i++) {
                queryString += arrayPalavras[i].trim();
                if (i < arrayPalavras.length - 1) {
                    queryString += " OR ";
                }
            }

            Query query = new Query(queryString);

        }

        Button buttonParar = findViewById(R.id.buttonPararColeta);

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