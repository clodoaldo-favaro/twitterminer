package entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Tweet {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_usuario")
    public int idUsuario;

    @ColumnInfo(name = "id_tweeter")
    public String idTweeter;

    @ColumnInfo(name = "mensagem")
    public String mensagem;

    @ColumnInfo(name = "data")
    public Date data;
}
