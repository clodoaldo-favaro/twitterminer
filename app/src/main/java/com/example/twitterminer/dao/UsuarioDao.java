package com.example.twitterminer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.twitterminer.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE login = :first LIMIT 1")
    Usuario findByLogin(String first);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Usuario user);
}
