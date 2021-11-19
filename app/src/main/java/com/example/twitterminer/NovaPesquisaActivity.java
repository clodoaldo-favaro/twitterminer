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

    Button buttonSalvarPesquisa;
    TextView tituloPesquisa;
    TextView descricaoPesquisa;
    TextView palavrasChave;
    TextView respostas;
    AppDatabase db;
    Context context;
    int duration = Toast.LENGTH_SHORT;

    private static final String TAG = "NovaPesquisaActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_pesquisa);

        context = getApplicationContext();

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

        String titulo = tituloPesquisa.getText().toString();
        String descricao = descricaoPesquisa.getText().toString();
        String palavras = palavrasChave.getText().toString();
        String resp = respostas.getText().toString();

        Pesquisa pesquisa = new Pesquisa();
        pesquisa.titulo = titulo;
        pesquisa.descricao = descricao;
        pesquisa.palavrasChave = palavras;
        pesquisa.respostasPossiveis = resp;

        //Log.i(TAG, pesquisa.toString());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            db = AppDatabase.getDatabase(getApplicationContext());
            db.pesquisaDao().insert(pesquisa);
            Pesquisa pesquisaGravada = db.pesquisaDao().findByTitle(titulo);
            if (pesquisaGravada != null) {
                //Log.i(TAG, "Gravou " + pesquisaGravada.toString());
                ContextCompat.getMainExecutor(context).execute(()  -> {
                    Toast toast = Toast.makeText(context, "Pesquisa salva com sucesso!", duration);
                    toast.show();
                });
                startActivity(new Intent(getApplicationContext(), com.example.twitterminer.TelaInicialActivity.class));
            }
        });
    }
}