package com.example.twitterminer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.repositories.AppRepository;

import java.util.Calendar;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

public class ColetarTweetsActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESQUISA = "pesquisa.id";
    public static final String EXTRA_PALAVRAS_CHAVE = "pesquisa.palavras_chave";
    public static final String EXTRA_RESPOSTAS = "pesquisa.respostas";
    public static final String EXTRA_ID_ULTIMO_TWEET = "pesquisa.idUltimoTweet";
    private final static int TWEETS_IN_PAGE = 100;


    private Pesquisa pesquisaTela;
    private Twitter twitter;
    private AppRepository mRepository;
    private boolean interruptSearch;
    private Thread thread;
    private int contadorTweets;
    private int idPesquisa;
    private TextView textViewContadorTweets;
    private  long idUltimoTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_tweets);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Coletar tweets");

        mRepository = new AppRepository(this.getApplication());
        Button buttonParar = findViewById(R.id.buttonPararColeta);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {
            idPesquisa = intent.getIntExtra(EXTRA_ID_PESQUISA, 0);
            idUltimoTweet = intent.getLongExtra(EXTRA_ID_ULTIMO_TWEET, 0);

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("qj1cYDaLij8xrSGRzwcrAzJlM")
                    .setOAuthConsumerSecret("zSx7g9w6S7BUE7jruTgQOlNMg1ISdVnZ4iHSTX3rm9Ntsw9Yx7")
                    .setOAuthAccessToken("2202275389-RCGdO6XK0QL0phOW2G70Svx57CzursY9iTxPMvs")
                    .setOAuthAccessTokenSecret("4gj9rnaABXdT6gy5E6XPGM8BMg69MxKQjRXZaBHIUcQFw");

            OAuthAuthorization auth = new OAuthAuthorization(cb.build());

            textViewContadorTweets = findViewById(R.id.textViewContadorTweetsColetados);

            twitter = new TwitterFactory().getInstance(auth);

            String palavrasChave = intent.getStringExtra(EXTRA_PALAVRAS_CHAVE);
            String[] arrayPalavras = palavrasChave.split(",");
            String queryString = "(";
            for (int i = 0; i < arrayPalavras.length; i++) {
                queryString += arrayPalavras[i].trim().toLowerCase();
                if (i < arrayPalavras.length - 1) {
                    queryString += " AND ";
                }
            }
            queryString += ") AND (";

            String respostas = intent.getStringExtra(EXTRA_RESPOSTAS);
            String[] arrayRespostas = respostas.split(",");
            for (int i = 0; i < arrayRespostas.length; i++) {
                arrayRespostas[i] = arrayRespostas[i].trim().toLowerCase();
                queryString += arrayRespostas[i];
                if (i < arrayRespostas.length - 1) {
                    queryString += " OR ";
                }
            }
            queryString += ") +exclude:retweets";

            interruptSearch = false;

            Query query = new Query(queryString);
            query.setCount(TWEETS_IN_PAGE);
            if (idUltimoTweet != 0) {
                query.setSinceId(idUltimoTweet);
            }

            thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    pesquisaTela = mRepository.getPesquisaById(idPesquisa);

                    //10000 tweets (100 por página x 5)
                    contadorTweets = 0;
                    for (int i = 0; i <= 100; i++) {
                        if (interruptSearch) {
                            break;
                        }
                        try {
                            QueryResult result = twitter.search(query);
                            for (Status tweet : result.getTweets()) {
                                String textoTweet = tweet.getText().toLowerCase();
                                if (contadorTweets == 0) {
                                    idUltimoTweet = tweet.getId();
                                }
                                contadorTweets++;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewContadorTweets.setText(Integer.toString(contadorTweets));
                                    }
                                });

                                for (int j = 0; j < arrayRespostas.length; j++) {
                                    if (textoTweet.contains(arrayRespostas[j])) {
                                        Resultado resultado = new Resultado();
                                        resultado.idPesquisa = idPesquisa;
                                        resultado.valorResposta = arrayRespostas[j];
                                        //Log.d("TEXTO_TWEET_RESPOSTA", textoTweet);
                                        mRepository.insertResultado(resultado);
                                    }
                                }
                            }
                        } catch (TwitterException te) {
                            te.printStackTrace();
                            //Log.d("TWITTER_EXCEPTION", te.getMessage());
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewContadorTweets.setText("Não há mais tweets a serem lidos");
                            buttonParar.setText("VOLTAR");
                        }
                    });
                }
            });
            thread.start();
        }


        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interromperColeta();
            }
        });
    }

    public void interromperColeta() {
        interruptSearch = true;
        if (thread != null) {
            thread.interrupt();
        }

        pesquisaTela.dataUltimaConsulta = Calendar.getInstance().getTime();
        pesquisaTela.idUltimoTweetConsultado = idUltimoTweet;
        mRepository.updatePesquisa(pesquisaTela);

        Intent intent = new Intent(ColetarTweetsActivity.this, PesquisaDetalheActivity.class);
        intent.putExtra(PesquisaDetalheActivity.EXTRA_ID_PESQUISA, idPesquisa);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        interromperColeta();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                interromperColeta();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}