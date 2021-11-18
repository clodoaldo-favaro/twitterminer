package com.example.twitterminer.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.dao.ResultadoDao;
import com.example.twitterminer.dao.TweetDao;
import com.example.twitterminer.dao.UsuarioDao;
import com.example.twitterminer.database.AppDatabase;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Usuario;

import java.util.List;

public class AppRepository {
    private UsuarioDao mUsuarioDao;
    private TweetDao mTweetDao;
    private PesquisaDao mPesquisaDao;
    private ResultadoDao mResultadoDao;

    public LiveData<List<Pesquisa>> mPesquisas;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUsuarioDao = db.usuarioDao();
        mTweetDao = db.tweetDao();
        mPesquisaDao = db.pesquisaDao();
        mResultadoDao = db.resultadoDao();
        mPesquisas = mPesquisaDao.getAll();
    }

    public void insertUser(Usuario usuario) {
        AppDatabase.databaseWriteExecutor.execute(() -> mUsuarioDao.insert(usuario));
    }

    public void insertPesquisa(Pesquisa pesquisa) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPesquisaDao.insert(pesquisa));
    }
}
