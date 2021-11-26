package com.example.twitterminer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TweetListViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewTextoTweet;

    public TweetListViewHolder(View view) {
        super(view);
        textViewTextoTweet = view.findViewById(R.id.textViewTextoTweet);
    }

    public void setTweetText (String text) { textViewTextoTweet.setText(text);}

    static TweetListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylclerview_tweet_row, parent, false);
        return new TweetListViewHolder(view);
    }
}
