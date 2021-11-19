package com.example.twitterminer.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Resultado {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_pesquisa")
    public int idPesquisa;

    @ColumnInfo(name = "valor_resposta")
    public String valorResposta;
}
