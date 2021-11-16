package com.example.twitterminer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.dao.ResultadoDao;
import com.example.twitterminer.dao.TweetDao;
import com.example.twitterminer.dao.UsuarioDao;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.entities.Usuario;

@Database(entities = {Usuario.class, Resultado.class, Tweet.class, Pesquisa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract PesquisaDao pesquisaDao();
    public abstract TweetDao tweetDao();
    public abstract ResultadoDao resultadoDao();
}
