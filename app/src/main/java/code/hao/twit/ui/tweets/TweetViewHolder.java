package code.hao.twit.ui.tweets;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.databinding.ItemTweetBinding;

public class TweetViewHolder extends RecyclerView.ViewHolder {

    private final ItemTweetBinding binding;

    public TweetViewHolder(@NonNull ItemTweetBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(final Tweet tweet) {
        binding.setTweet(tweet);
        // movie click event
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
            }
        });

        binding.executePendingBindings();
    }

    public static TweetViewHolder create(ViewGroup parent) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemTweetBinding binding =
                ItemTweetBinding.inflate(layoutInflater, parent, false);
        return new TweetViewHolder(binding);
    }
}
