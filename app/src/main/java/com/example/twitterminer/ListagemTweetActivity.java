package com.example.twitterminer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.repositories.AppRepository;
import com.example.twitterminer.viewmodel.PesquisaViewModel;
import com.example.twitterminer.viewmodel.TweetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

public class ListagemTweetActivity extends AppCompatActivity {

    FloatingActionButton novoTweetButton;
    public static final int NOVO_TWEET_ACTIVITY_REQUEST_CODE = 1;
    private TweetViewModel mTweetViewModel;
    private TextView noResultsTextView;
    private RecyclerView recyclerView;
    private TweetListAdapter tweetListAdapter;
    private AppRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_tweets);

        noResultsTextView = findViewById(R.id.textViewNoTweets);
        recyclerView = findViewById(R.id.recyclerViewTweet);

        novoTweetButton = findViewById(R.id.adicionarTweetFab);
        mRepository = new AppRepository(this.getApplication());

        novoTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListagemTweetActivity.this, NovoTweetActivity.class);

                startActivityForResult(intent, NOVO_TWEET_ACTIVITY_REQUEST_CODE);
            }
        });

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mTweetViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TweetViewModel.class);

        mTweetViewModel.getAllTweetsLiveData().observe(this, tweets ->  {
            if (tweets.size() == 0) {
                noResultsTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tweetListAdapter.submitList(tweets);
                recyclerView.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView() {
        tweetListAdapter = new TweetListAdapter(new TweetListAdapter.TweetDiff());
        recyclerView.setAdapter(tweetListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Context context = getApplicationContext();

        if (requestCode == NOVO_TWEET_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Tweet tweet = new Tweet();
            tweet.mensagem = data.getStringExtra(NovoTweetActivity.EXTRA_MENSAGEM);

            mTweetViewModel.insert(tweet);

            Toast.makeText(
                    context,
                    "Tweet salvo com sucesso!",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Não foi possível salvar o tweet",
                    Toast.LENGTH_LONG).show();
        }
    }
}