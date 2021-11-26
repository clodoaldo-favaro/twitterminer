package com.example.twitterminer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

public class NovoTweetActivity extends AppCompatActivity {
    public static String EXTRA_MENSAGEM = "tweet.novo";


    Button buttonSalvarTweet;
    TextView mensagemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_tweet);

        buttonSalvarTweet = findViewById(R.id.buttonSalvarTweet);

        buttonSalvarTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarTweet();
            }
        });
    }

    private void salvarTweet() {
        mensagemView = findViewById(R.id.editTextNovoTweet);
        String mensagem = mensagemView.getText().toString();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("qj1cYDaLij8xrSGRzwcrAzJlM")
                .setOAuthConsumerSecret("zSx7g9w6S7BUE7jruTgQOlNMg1ISdVnZ4iHSTX3rm9Ntsw9Yx7")
                .setOAuthAccessToken("2202275389-RCGdO6XK0QL0phOW2G70Svx57CzursY9iTxPMvs")
                .setOAuthAccessTokenSecret("4gj9rnaABXdT6gy5E6XPGM8BMg69MxKQjRXZaBHIUcQFw");

        OAuthAuthorization auth = new OAuthAuthorization(cb.build());

        Twitter twitter = new TwitterFactory().getInstance(auth);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Status status = twitter.updateStatus(mensagem);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_MENSAGEM, mensagem);

        setResult(RESULT_OK, intent);
        finish();
    }
}