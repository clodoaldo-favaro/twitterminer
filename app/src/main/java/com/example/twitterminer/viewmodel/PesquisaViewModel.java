package com.example.twitterminer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.repositories.AppRepository;

import java.util.List;

public class PesquisaViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    private MutableLiveData<List<Pesquisa>> listOfPesquisas;
    private final LiveData<List<Pesquisa>> mAllPesquisas;

    public PesquisaViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllPesquisas = mRepository.getAllPesquisasLiveData();
        listOfPesquisas = new MutableLiveData<>();
    }

    public LiveData<List<Pesquisa>> getAllPesquisasLiveData() { return mAllPesquisas; }

    public MutableLiveData<List<Pesquisa>> getListOfPesquisasObserver() {
        getAllPesquisaList();
        return listOfPesquisas;
    }

    public void getAllPesquisaList() {
        List<Pesquisa> pesquisas = mRepository.getAllPesquisas();
        if (pesquisas.size() > 0) {
            listOfPesquisas.postValue(pesquisas);
        } else {
            listOfPesquisas.postValue(null);
        }
    }

    public void insert(Pesquisa pesquisa) {
        mRepository.insertPesquisa(pesquisa);
    }

    public void delete(Pesquisa pesquisa) {
        mRepository.deletePesquisa(pesquisa);
    }


}
