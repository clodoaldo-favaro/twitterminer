package com.example.twitterminer;

import android.content.Intent;
import android.os.Bundle;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.viewmodel.PesquisaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.twitterminer.databinding.ActivityListagemPesquisasBinding;

public class ListagemPesquisasActivity extends AppCompatActivity {

    FloatingActionButton novaPesquisaButton;
    public static final int NOVA_PESQUISA_ACTIVITY_REQUEST_CODE = 1;
    private PesquisaViewModel mPesquisaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pesquisas);

        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);

        novaPesquisaButton = findViewById(R.id.adicionarPesquisaFab);

        novaPesquisaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListagemPesquisasActivity.this, NovaPesquisaActivity.class);

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