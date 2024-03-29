package com.example.twitterminer.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "senha")
    public String senha;

    @ColumnInfo(name = "api_key")
    public String apiKey;

    @ColumnInfo(name = "api_key_secret")
    public String apyKeySecret;

    @ColumnInfo(name = "access_token")
    public String accessToken;

    @ColumnInfo(name = "access_token_secret")
    public String accessTokenSecret;

    public Usuario() {};

    public Usuario(String nome) {
        this.nome = nome;
    }



    public static Usuario getDefaultUser() {
        Usuario usuario = new Usuario("Default");
        usuario.login = "teste";
        usuario.senha = "123456";
        usuario.apiKey= "";
        usuario.apyKeySecret = "";
        usuario.accessToken = "";
        usuario.accessTokenSecret = "";

        return usuario;
    }
}
