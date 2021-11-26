package com.example.twitterminer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.twitterminer.entities.Pesquisa;
import com.example.twitterminer.entities.Tweet;

import java.util.List;

public class TweetListAdapter extends ListAdapter<Tweet, TweetListViewHolder> {

    private Context context;
    private List<Tweet> tweetList;

    public TweetListAdapter(@NonNull DiffUtil.ItemCallback<Tweet> diffCallback) {
        super(diffCallback);
    }

    @Override
    public TweetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TweetListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TweetListViewHolder holder, int position) {
        Tweet current = getItem(position);
        holder.setTweetText(current.mensagem);
    }

    static class TweetDiff extends DiffUtil.ItemCallback<Tweet> {

        @Override
        public boolean areItemsTheSame(@NonNull Tweet oldItem, @NonNull Tweet newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tweet oldItem, @NonNull Tweet newItem) {
            return oldItem.mensagem.equals(newItem.mensagem);
        }
    }
}
