package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twitterminer.database.AppDatabase;
import com.example.twitterminer.entities.Pesquisa;

public class NovaPesquisaActivity extends AppCompatActivity {

    public static final String EXTRA_TITULO = "novapesquisa.TITULO";
    public static final String EXTRA_DESCRICAO = "novapesquisa.DESCRICAO";
    public static final String EXTRA_PALAVRAS_CHAVE = "novapesquisa.PALAVRAS_CHAVE";
    public static final String EXTRA_RESPOSTAS = "novapesquisa.RESPOSTAS";

    Button buttonSalvarPesquisa;
    TextView tituloPesquisa;
    TextView descricaoPesquisa;
    TextView palavrasChave;
    TextView respostas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_pesquisa);

        buttonSalvarPesquisa = findViewById(R.id.buttonSalvarPesquisa);

        buttonSalvarPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarPesquisa();
            }
        });
    }

    private void salvarPesquisa() {
        tituloPesquisa = findViewById(R.id.editTextTituloPesquisa);
        descricaoPesquisa = findViewById(R.id.editTextDescricao);
        palavrasChave = findViewById(R.id.editTextPalavrasChave);
        respostas = findViewById(R.id.editTextRespostas);

        String titulo = tituloPesquisa.getText().toString().trim();
        String palavras = palavrasChave.getText().toString().trim();
        String resp = respostas.getText().toString().trim();

        if (titulo.isEmpty() || palavras.isEmpty() || resp.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Campos não preenchidos!",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITULO, tituloPesquisa.getText().toString());
            intent.putExtra(EXTRA_DESCRICAO, descricaoPesquisa.getText().toString());
            intent.putExtra(EXTRA_PALAVRAS_CHAVE, palavrasChave.getText().toString());
            intent.putExtra(EXTRA_RESPOSTAS, respostas.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}