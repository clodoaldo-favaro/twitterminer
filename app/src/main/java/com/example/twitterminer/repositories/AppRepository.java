package com.example.twitterminer.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.dao.ResultadoDao;
import com.example.twitterminer.dao.TweetDao;
import com.example.twitterminer.dao.UsuarioDao;
import com.example.twitterminer.database.AppDatabase;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.entities.Usuario;

import java.util.List;

public class AppRepository {
    private UsuarioDao mUsuarioDao;
    private TweetDao mTweetDao;
    private PesquisaDao mPesquisaDao;
    private ResultadoDao mResultadoDao;

    public LiveData<List<Pesquisa>> mPesquisas;
    public LiveData<List<Tweet>> mTweets;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTweetDao = db.tweetDao();
        mPesquisaDao = db.pesquisaDao();
        mResultadoDao = db.resultadoDao();
        mPesquisas = mPesquisaDao.getAllLiveData();
        mTweets = mTweetDao.getAllLiveData();
    }


    public void insertPesquisa(Pesquisa pesquisa) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPesquisaDao.insert(pesquisa));
    }

    public void insertTweet(Tweet tweet) {
        AppDatabase.databaseWriteExecutor.execute(() -> mTweetDao.insert(tweet));
    }
}
