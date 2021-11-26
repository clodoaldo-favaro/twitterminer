package com.example.twitterminer.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.dao.ResultadoDao;
import com.example.twitterminer.dao.TweetDao;
import com.example.twitterminer.dao.UsuarioDao;
import com.example.twitterminer.database.AppDatabase;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.entities.Usuario;
import com.example.twitterminer.pojo.ResultadoPojo;

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

    public void updatePesquisa(Pesquisa pesquisa) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPesquisaDao.update(pesquisa));
    }

    public void insertResultado(Resultado resultado) {
        AppDatabase.databaseWriteExecutor.execute(() -> mResultadoDao.insert(resultado));
    }

    public ResultadoPojo getTotalResultadosByIdPesquisa(int idPesquisa) {
        return mResultadoDao.getTotalResultadosByIdPesquisa(idPesquisa);
    }

    public List<ResultadoPojo> getSomatorioResultadosByIdPesquisa(int idPesquisa) {
        return mResultadoDao.getSomatorioResultadosByIdPesquisa(idPesquisa);
    }

    public void insertTweet(Tweet tweet) {
        AppDatabase.databaseWriteExecutor.execute(() -> mTweetDao.insert(tweet));
    }

    public LiveData<List<Pesquisa>> getAllPesquisasLiveData() {
        return mPesquisaDao.getAllLiveData();
    }

    public LiveData<List<Tweet>> getAllTweetsLiveData() {
        return mTweetDao.getAllLiveData();
    }

    public Pesquisa getPesquisaById(int id) {
        return mPesquisaDao.findById(id);
    }

    public LiveData<Pesquisa> getPesquisaLiveData(int id) {
        return mPesquisaDao.findByIdLiveData(id);
    }

    public void deletePesquisa(Pesquisa pesquisa) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPesquisaDao.delete(pesquisa));
    }

    public void deleteResultadosByIdPesquisa(int idPesquisa) {
        AppDatabase.databaseWriteExecutor.execute(() -> mResultadoDao.deleteByIdPesquisa(idPesquisa));
    }
}
