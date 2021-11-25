package com.example.twitterminer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.pojo.ResultadoPojo;

import java.util.List;

@Dao
public interface ResultadoDao {
    @Query("SELECT * FROM resultado")
    List<Resultado> getAll();

    @Query("SELECT * FROM resultado WHERE id = :id")
    Resultado findById(int id);

    @Query("SELECT * FROM resultado WHERE id_pesquisa = :idPesquisa LIMIT 1")
    Resultado findByIdPesquisa(int idPesquisa);

    @Query("SELECT valor_resposta, count(*) as contagem FROM resultado WHERE id_pesquisa = :idPesquisa GROUP BY valor_resposta ORDER BY contagem desc")
    List<ResultadoPojo> getSomatorioResultadosByIdPesquisa(int idPesquisa);

    @Query("SELECT count(*) as contagem FROM resultado WHERE id_pesquisa = :idPesquisa GROUP BY valor_resposta ORDER BY contagem desc")
    ResultadoPojo getTotalResultadosByIdPesquisa(int idPesquisa);

    @Insert
    void insert(Resultado resultado);

    @Delete
    void delete(Resultado resultado);

    @Query("DELETE FROM resultado")
    void deleteAll();
}
