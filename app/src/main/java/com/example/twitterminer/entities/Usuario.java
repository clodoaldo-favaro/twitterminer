package com.example.twitterminer.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "login")
    public String login;

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
}
