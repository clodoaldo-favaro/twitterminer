package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.repositories.AppRepository;

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
    private final static int TWEETS_IN_PAGE = 100;


    private Pesquisa pesquisaTela;
    private Twitter twitter;
    private AppRepository mRepository;
    private boolean interruptSearch;
    private Thread thread;
    private int contadorTweets;
    private TextView textViewContadorTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_tweets);

        mRepository = new AppRepository(getApplication());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID_PESQUISA)) {
            int idPesquisa = intent.getIntExtra(EXTRA_ID_PESQUISA, 0);

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
                queryString += arrayPalavras[i].trim();
                if (i < arrayPalavras.length - 1) {
                    queryString += " AND ";
                }
            }
            queryString += ") AND (";

            String respostas = intent.getStringExtra(EXTRA_RESPOSTAS);
            String[] arrayRespostas = respostas.split(",");
            for (int i = 0; i < arrayRespostas.length; i++) {
                arrayRespostas[i] = arrayRespostas[i].trim();
                queryString += arrayRespostas[i];
                if (i < arrayRespostas.length - 1) {
                    queryString += " OR ";
                }
            }
            queryString += ") +exclude:retweets";

            Resultado resultado = new Resultado();
            resultado.idPesquisa = idPesquisa;
            interruptSearch = false;

            Query query = new Query(queryString);
            query.setCount(TWEETS_IN_PAGE);

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    //500 tweets (100 por página x 5)
                    contadorTweets = 0;
                    for (int i = 0; i <= 5; i++) {
                        try {
                            QueryResult result = twitter.search(query);
                            for (Status tweet : result.getTweets()) {
                                String textoTweet = tweet.getText();
                                contadorTweets++;
                                textViewContadorTweets.setText(contadorTweets);
                                for (int j = 0; j < arrayRespostas.length; j++) {
                                    if (textoTweet.contains(arrayRespostas[j])) {
                                        resultado.valorResposta = arrayRespostas[j];
                                        Log.d("TEXTO_TWEET_RESPOSTA", textoTweet);
                                        mRepository.insertResultado(resultado);
                                    }
                                }
                            }
                        } catch (TwitterException te) {
                            te.printStackTrace();
                            Log.d("TWITTER_EXCEPTION", te.getMessage());
                        }
                    }
                }
            });
            thread.start();
        }

        Button buttonParar = findViewById(R.id.buttonPararColeta);

        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread != null) {
                    thread.interrupt();
                }
                Intent intent = new Intent(ColetarTweetsActivity.this, PesquisaDetalheActivity.class);
                intent.putExtra(PesquisaDetalheActivity.EXTRA_ID_PESQUISA, EXTRA_ID_PESQUISA);
                startActivity(intent);
            }
        });
    }
}