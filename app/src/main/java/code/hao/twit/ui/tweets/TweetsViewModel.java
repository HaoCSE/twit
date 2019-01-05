package code.hao.twit.ui.tweets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import code.hao.twit.data.TweetRepository;
import code.hao.twit.data.local.model.Tweet;

import java.util.List;

public class TweetsViewModel extends ViewModel {
    private TweetRepository movieRepository;
    private LiveData<List<Tweet>> tweetListLiveData;

    public TweetsViewModel(final TweetRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.tweetListLiveData = movieRepository.getTweets();

    }

    public LiveData<List<Tweet>> getTweetListLiveData() {
        return tweetListLiveData;
    }

    public void insertTweet(Tweet tweet){
        this.movieRepository.insertTweet(tweet);
    }
}
