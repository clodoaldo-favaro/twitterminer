package com.example.twitterminer.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Pesquisa {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_usuario")
    public int idUsuario;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "descricao")
    public String descricao;

    @ColumnInfo(name = "palavras_chave")
    public String palavrasChave;

    @ColumnInfo(name = "respostas_possiveis")
    public String respostasPossiveis;

    @ColumnInfo(name = "data_inicio")
    public Date dataInicio;

    @ColumnInfo(name = "data_ultima_consulta")
    public Date dataUltimaConsulta;

    public Pesquisa() {};

    @Override
    public String toString() {
        return "Pesquisa{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", palavrasChave='" + palavrasChave + '\'' +
                ", respostasPossiveis='" + respostasPossiveis + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataUltimaConsulta=" + dataUltimaConsulta +
                '}';
    }
}
