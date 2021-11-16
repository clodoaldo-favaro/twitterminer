package com.example.twitterminer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twitterminer.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM usuario WHERE id IN (:userIds)")
    List<Usuario> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM usuario WHERE nome LIKE :first LIMIT 1")
    Usuario findByName(String first);

    @Query("SELECT * FROM usuario WHERE login = :first LIMIT 1")
    Usuario findByLogin(String first);

    @Query("SELECT * FROM usuario WHERE email = :first LIMIT 1")
    Usuario findByEmail(String first);

    @Insert
    void insertAll(Usuario... users);

    @Delete
    void delete(Usuario user);
}
