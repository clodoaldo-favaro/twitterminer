package com.example.twitterminer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.repositories.AppRepository;

import java.util.List;

public class PesquisaViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    private final LiveData<List<Pesquisa>> mPesquisas;

    public PesquisaViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mPesquisas = mRepository.mPesquisas;
    }

    LiveData<List<Pesquisa>> getPesquisas() {
        return mPesquisas;
    }

    public void insertPesquisa(Pesquisa pesquisa) {
        mRepository.insertPesquisa(pesquisa);
    }


}
