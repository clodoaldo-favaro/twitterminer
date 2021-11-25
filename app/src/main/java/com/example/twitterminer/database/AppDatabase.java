package com.example.twitterminer.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twitterminer.dao.PesquisaDao;
import com.example.twitterminer.dao.ResultadoDao;
import com.example.twitterminer.dao.TweetDao;
import com.example.twitterminer.dao.UsuarioDao;
import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Resultado;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.entities.Usuario;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Usuario.class, Resultado.class, Tweet.class, Pesquisa.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract PesquisaDao pesquisaDao();
    public abstract TweetDao tweetDao();
    public abstract ResultadoDao resultadoDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sCreateRoomDatabaseCallback)
                            //.addCallback(sOpenRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback sCreateRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UsuarioDao usuarioDao = INSTANCE.usuarioDao();

                Usuario usuario = usuarioDao.findByLogin("teste");
                if (usuario == null) {
                    usuario = Usuario.getDefaultUser();
                    usuarioDao.insert(usuario);
                }
            });
        }
    };

    public static RoomDatabase.Callback sOpenRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                ResultadoDao resultadoDao = INSTANCE.resultadoDao();
                resultadoDao.deleteAll();

            });
        }
    };
}
