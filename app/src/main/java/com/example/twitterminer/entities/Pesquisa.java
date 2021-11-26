package com.example.twitterminer.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import javax.annotation.Nonnegative;

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

    @ColumnInfo(name = "data_inclusao")
    public Date dataInclusao;

    @ColumnInfo(name = "data_ultima_consulta")
    public Date dataUltimaConsulta;

    @ColumnInfo(name="id_ultimo_tweet_consultado")
    public long idUltimoTweetConsultado;

    public Pesquisa() {};

    @NonNull
    public String getTitulo() { return this.titulo; }

    public String getDescricao() { return this.descricao; }

    @Override
    public String toString() {
        return "Pesquisa{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", palavrasChave='" + palavrasChave + '\'' +
                ", respostasPossiveis='" + respostasPossiveis + '\'' +
                ", dataInicio=" + dataInclusao +
                ", dataUltimaConsulta=" + dataUltimaConsulta +
                '}';
    }
}
