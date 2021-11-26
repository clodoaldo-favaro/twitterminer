package com.example.twitterminer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Tweet;
import com.example.twitterminer.repositories.AppRepository;

import java.util.List;

public class TweetViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    private final LiveData<List<Tweet>> mAllTweets;

    public TweetViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllTweets = mRepository.getAllTweetsLiveData();
    }

    public LiveData<List<Tweet>> getAllTweetsLiveData() { return mAllTweets; }

    public void insert(Tweet tweet) {
        mRepository.insertTweet(tweet);
    }
}
