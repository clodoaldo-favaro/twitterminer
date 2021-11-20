package com.example.twitterminer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twitterminer.entities.Tweet;

import java.util.List;

@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweet")
    List<Tweet> getAll();

    @Query("SELECT * FROM tweet")
    LiveData<List<Tweet>> getAllLiveData();

    @Query("SELECT * FROM tweet WHERE id = :id")
    Tweet findById(int id);

    @Query("SELECT * FROM tweet WHERE mensagem LIKE :mensagem LIMIT 1")
    Tweet findByMensagem(String mensagem);

    @Insert
    void insert(Tweet tweet);

    @Delete
    void delete(Tweet tweet);
}
