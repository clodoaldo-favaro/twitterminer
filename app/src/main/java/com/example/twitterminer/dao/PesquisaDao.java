package com.example.twitterminer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twitterminer.entities.Pesquisa;

import java.util.List;

@Dao
public interface PesquisaDao {
    @Query("SELECT * FROM pesquisa")
    List<Pesquisa> getAll();

    @Query("SELECT * FROM pesquisa WHERE id = :id")
    Pesquisa findById(int id);

    @Query("SELECT * FROM pesquisa WHERE id IN (:pesquisaIds)")
    List<Pesquisa> loadAllByIds(int[] pesquisaIds);

    @Query("SELECT * FROM pesquisa WHERE titulo LIKE :titulo LIMIT 1")
    Pesquisa findByTitle(String titulo);

    @Query("SELECT * FROM pesquisa WHERE descricao LIKE :descricao LIMIT 1")
    Pesquisa findByDescricao(String descricao);

    @Insert
    void insertAll(Pesquisa... pesquisa);

    @Delete
    void delete(Pesquisa pesquisa);
}
