package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.viewmodel.PesquisaViewModel;

public class TelaInicialActivity extends AppCompatActivity {

    Button pesquisasButton;
    Button tweetsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        pesquisasButton = findViewById(R.id.buttonPesquisas);

        pesquisasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicialActivity.this, ListagemPesquisasActivity.class);
                startActivity(intent);
            }
        });

        tweetsButton = findViewById(R.id.buttonTweets);

        tweetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicialActivity.this, ListagemTweetActivity.class);
                startActivity(intent);
            }
        });
    }
}