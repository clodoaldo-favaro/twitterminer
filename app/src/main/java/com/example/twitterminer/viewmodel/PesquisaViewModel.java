package com.example.twitterminer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.repositories.AppRepository;

import java.util.List;

public class PesquisaViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    private final LiveData<List<Pesquisa>> mAllPesquisas;

    public PesquisaViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllPesquisas = mRepository.getAllPesquisasLiveData();
    }

    public LiveData<List<Pesquisa>> getAllPesquisasLiveData() { return mAllPesquisas; }
    public LiveData<Pesquisa> getPesquisaLiveDataById(int id) { return mRepository.getPesquisaLiveData(id); }

    public void insert(Pesquisa pesquisa) {
        mRepository.insertPesquisa(pesquisa);
    }

    public void delete(Pesquisa pesquisa) {
        mRepository.deletePesquisa(pesquisa);
    }

}
