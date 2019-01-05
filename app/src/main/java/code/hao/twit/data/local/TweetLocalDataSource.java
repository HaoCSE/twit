package code.hao.twit.data.local;

import androidx.lifecycle.LiveData;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.utils.AppExecutors;

import java.util.List;

public class TweetLocalDataSource {
    private final TweetDatabase mDatabase;
    private static volatile TweetLocalDataSource sInstance;

    public static TweetLocalDataSource getInstance(TweetDatabase database) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new TweetLocalDataSource(database);
                }
            }
        }
        return sInstance;
    }

    public TweetLocalDataSource(TweetDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

    public LiveData<List<Tweet>> getTweets() {
        return mDatabase.tweetsDao().getTweets();
    }

    public void insertTweet(Tweet tweet) {
        mDatabase.tweetsDao().insertTweet(tweet);
    }
}
