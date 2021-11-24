package com.example.twitterminer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.viewmodel.PesquisaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterminer.databinding.ActivityListagemPesquisasBinding;

import java.util.List;

public class ListagemPesquisasActivity extends AppCompatActivity implements PesquisaListAdapter.HandlePesquisaClick {

    FloatingActionButton novaPesquisaButton;
    public static final int NOVA_PESQUISA_ACTIVITY_REQUEST_CODE = 1;
    private PesquisaViewModel mPesquisaViewModel;
    private TextView noResultsTextView;
    private RecyclerView recyclerView;
    private PesquisaListAdapter pesquisaListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pesquisas);

        noResultsTextView = findViewById(R.id.textViewNoPesquisas);
        recyclerView = findViewById(R.id.recyclerViewPesquisas);

        novaPesquisaButton = findViewById(R.id.adicionarPesquisaFab);

        novaPesquisaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListagemPesquisasActivity.this, NovaPesquisaActivity.class);

                startActivityForResult(intent, NOVA_PESQUISA_ACTIVITY_REQUEST_CODE);
            }
        });

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);

        mPesquisaViewModel.getAllPesquisasLiveData().observe(this, pesquisas ->  {
            if (pesquisas.size() == 0) {
                noResultsTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                pesquisaListAdapter.submitList(pesquisas);
                recyclerView.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView() {
        pesquisaListAdapter = new PesquisaListAdapter(new PesquisaListAdapter.PesquisaDiff(), this);
        recyclerView.setAdapter(pesquisaListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Context context = getApplicationContext();

        if (requestCode == NOVA_PESQUISA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Pesquisa pesquisa = new Pesquisa();
            pesquisa.titulo = data.getStringExtra(NovaPesquisaActivity.EXTRA_TITULO);
            pesquisa.descricao = data.getStringExtra(NovaPesquisaActivity.EXTRA_DESCRICAO);
            pesquisa.palavrasChave = data.getStringExtra(NovaPesquisaActivity.EXTRA_PALAVRAS_CHAVE);
            pesquisa.respostasPossiveis = data.getStringExtra(NovaPesquisaActivity.EXTRA_RESPOSTAS);
            mPesquisaViewModel.insert(pesquisa);

            Toast.makeText(
                    context,
                    "Pesquisa salva com sucesso!",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Não foi possível salvar a pesquisa",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void itemClick(Pesquisa pesquisa) {

    }

    @Override
    public void removeItem(Pesquisa pesquisa) {
        mPesquisaViewModel.delete(pesquisa);
        Toast.makeText(
                getApplicationContext(),
                "Pesquisa excluída!",
                Toast.LENGTH_LONG).show();
    }
}