package code.hao.twit.ui.tweets;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.twit.data.local.model.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Tweet> mTweetsList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TweetViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tweet movie = mTweetsList.get(position);
        ((TweetViewHolder) holder).bindTo(movie);
    }

    @Override
    public int getItemCount() {
        return mTweetsList != null ? mTweetsList.size() : 0;
    }

    public void submitList(List<Tweet> tweets) {
        mTweetsList = tweets;
        notifyDataSetChanged();
    }


}
