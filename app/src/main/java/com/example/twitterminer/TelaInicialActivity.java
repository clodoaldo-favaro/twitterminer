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
    public static final int NOVA_PESQUISA_ACTIVITY_REQUEST_CODE = 1;
    public static final int NOVO_TWEET_ACTIVITY_REQUEST_CODE = 2;
    private PesquisaViewModel mPesquisaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //mPesquisaViewModel = new ViewModelProvider(this).get(PesquisaViewModel.class);
        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);

        pesquisasButton = findViewById(R.id.buttonPesquisas);

        pesquisasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicialActivity.this, NovaPesquisaActivity.class);

                startActivityForResult(intent, NOVA_PESQUISA_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOVA_PESQUISA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Pesquisa pesquisa = new Pesquisa();
            pesquisa.titulo = data.getStringExtra(NovaPesquisaActivity.EXTRA_TITULO);
            pesquisa.descricao = data.getStringExtra(NovaPesquisaActivity.EXTRA_DESCRICAO);
            pesquisa.palavrasChave = data.getStringExtra(NovaPesquisaActivity.EXTRA_PALAVRAS_CHAVE);
            pesquisa.respostasPossiveis = data.getStringExtra(NovaPesquisaActivity.EXTRA_RESPOSTAS);

            mPesquisaViewModel.insert(pesquisa);

            Toast.makeText(
                    getApplicationContext(),
                    "Pesquisa salva com sucesso!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Não foi possível salvar a pesquisa",
                    Toast.LENGTH_LONG).show();
        }
    }
}