package com.example.twitterminer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twitterminer.entities.Pesquisa;

import java.util.List;

@Dao
public interface PesquisaDao {
    @Query("SELECT * FROM pesquisa")
    List<Pesquisa> getAll();

    @Query("SELECT * FROM pesquisa")
    LiveData<List<Pesquisa>> getAllLiveData();

    @Query("SELECT * FROM pesquisa WHERE id = :id")
    Pesquisa findById(int id);

    @Query("SELECT * FROM pesquisa WHERE id = :id")
    LiveData<Pesquisa> findByIdLiveData(int id);

    @Query("SELECT * FROM pesquisa WHERE titulo LIKE :titulo LIMIT 1")
    Pesquisa findByTitle(String titulo);

    @Insert
    void insert(Pesquisa pesquisa);

    @Update
    void update(Pesquisa pesquisa);

    @Delete
    void delete(Pesquisa pesquisa);

    @Query("UPDATE pesquisa SET id_ultimo_tweet_consultado = 0")
    void resetIdUltimoTweet();
}
