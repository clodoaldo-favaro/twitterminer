package com.example.twitterminer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.pojo.ResultadoPojo;
import com.example.twitterminer.repositories.AppRepository;
import com.example.twitterminer.viewmodel.PesquisaViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PesquisaDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";

    private PesquisaViewModel mPesquisaViewModel;
    private TextView textViewTituloPesquisa;
    private TextView textViewDescricaoPesquisa;
    private TextView textViewResultado;
    private TextView textViewDataUltimaConsulta;
    private  Pesquisa pesquisaTela;
    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_detalhe);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Pesquisa");

        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);
        textViewTituloPesquisa = findViewById(R.id.textViewPesquisaDetalheTitulo);
        textViewDescricaoPesquisa = findViewById(R.id.textViewPesquisaDetalheDescricao);
        textViewDataUltimaConsulta = findViewById(R.id.textViewDataUltimaPesquisa);
        mRepository = new AppRepository(this.getApplication());
        textViewResultado = findViewById(R.id.textViewResultados);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {
            int id = intent.getIntExtra(EXTRA_ID_PESQUISA, 0);

            mPesquisaViewModel.getPesquisaLiveDataById(id).observe(this, new Observer<Pesquisa>() {
                @Override
                public void onChanged(Pesquisa pesquisa) {
                    if (pesquisa != null) {
                        textViewTituloPesquisa.setText(pesquisa.getTitulo());
                        textViewDescricaoPesquisa.setText(pesquisa.getDescricao());
                        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String dataUltimaConsulta = "Última consulta: " + frmt.format(pesquisa.dataUltimaConsulta);
                        textViewDataUltimaConsulta.setText(dataUltimaConsulta);

                        pesquisaTela = pesquisa;
                    }
                }
            });

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ResultadoPojo totalResultado = mRepository.getTotalResultadosByIdPesquisa(id);
                    if (totalResultado != null && totalResultado.contagem > 0) {
                        List<ResultadoPojo> somatorioResultados = mRepository.getSomatorioResultadosByIdPesquisa(id);
                        Log.d("TESTE", "1");
                        String textResultado = "Tweets analisados: " + Integer.toString(totalResultado.contagem) + "\n";
                        for (int i = 0; i < somatorioResultados.size(); i++) {
                            textResultado += somatorioResultados.get(i).valorResposta + ": " + somatorioResultados.get(i).contagem + "\n";
                        }
                        String finalTextResultado = textResultado;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewResultado.setText(finalTextResultado);
                            }
                        });
                    }
                }
            });
            thread.start();
        }

        Button buttonColetar = findViewById(R.id.buttonColetarTweets);
        buttonColetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisaDetalheActivity.this, ColetarTweetsActivity.class);
                intent.putExtra(ColetarTweetsActivity.EXTRA_ID_PESQUISA, pesquisaTela.id);
                intent.putExtra(ColetarTweetsActivity.EXTRA_PALAVRAS_CHAVE, pesquisaTela.palavrasChave);
                intent.putExtra(ColetarTweetsActivity.EXTRA_RESPOSTAS, pesquisaTela.respostasPossiveis);
                intent.putExtra(ColetarTweetsActivity.EXTRA_ID_ULTIMO_TWEET, pesquisaTela.idUltimoTweetConsultado);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListagemPesquisasActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ListagemPesquisasActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}