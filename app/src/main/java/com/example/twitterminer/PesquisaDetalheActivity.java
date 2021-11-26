package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.pojo.ResultadoPojo;
import com.example.twitterminer.repositories.AppRepository;
import com.example.twitterminer.viewmodel.PesquisaViewModel;

import java.util.List;

public class PesquisaDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";

    private PesquisaViewModel mPesquisaViewModel;
    private TextView textViewTituloPesquisa;
    private TextView textViewDescricaoPesquisa;
    private TextView textViewResultado;
    private  Pesquisa pesquisaTela;
    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_detalhe);

        mPesquisaViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(PesquisaViewModel.class);
        textViewTituloPesquisa = findViewById(R.id.textViewPesquisaDetalheTitulo);
        textViewDescricaoPesquisa = findViewById(R.id.textViewPesquisaDetalheDescricao);
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
}