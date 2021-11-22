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
    private final LiveData<List<Pesquisa>> mPesquisas;
    private MutableLiveData<List<Pesquisa>> listOfPesquisas;

    public PesquisaViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mPesquisas = mRepository.mPesquisas;
        listOfPesquisas = new MutableLiveData<>();
    }

    public LiveData<List<Pesquisa>> getPesquisas() {
        return mPesquisas;
    }

    public MutableLiveData<List<Pesquisa>> getListOfPesquisasObserver() {
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
        getAllPesquisaList();
    }

    public void delete(Pesquisa pesquisa) {
        mRepository.deletePesquisa(pesquisa);
        getAllPesquisaList();
    }


}
