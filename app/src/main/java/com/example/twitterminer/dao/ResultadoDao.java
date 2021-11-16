package com.example.twitterminer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;

import java.util.List;

@Dao
public interface ResultadoDao {
    @Query("SELECT * FROM resultado")
    List<Resultado> getAll();

    @Query("SELECT * FROM resultado WHERE id = :id")
    Resultado findById(int id);

    @Query("SELECT * FROM resultado WHERE id IN (:resultadoIds)")
    List<Pesquisa> loadAllByIds(int[] resultadoIds);

    @Query("SELECT * FROM resultado WHERE id_pesquisa = :idPesquisa LIMIT 1")
    Resultado findByIdPesquisa(int idPesquisa);

    @Insert
    void insertAll(Resultado... resultado);

    @Delete
    void delete(Resultado resultado);
}
