package code.hao.twit.data;

import androidx.lifecycle.LiveData;
import code.hao.twit.data.local.TweetLocalDataSource;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.utils.AppExecutors;

import java.util.List;

public class TweetRepository implements DataSource{

    private final TweetLocalDataSource mLocalDataSource;
    private final AppExecutors mExecutors;

    private static volatile TweetRepository sInstance;

    public static TweetRepository getInstance(TweetLocalDataSource localDataSource,
                                              AppExecutors executors) {
        if (sInstance == null) {
            synchronized (TweetRepository.class) {
                if (sInstance == null) {
                    sInstance = new TweetRepository(localDataSource, executors);
                }
            }
        }
        return sInstance;
    }

    public TweetRepository(TweetLocalDataSource mLocalDataSource, AppExecutors mExecutors) {
        this.mLocalDataSource = mLocalDataSource;
        this.mExecutors = mExecutors;
    }

    @Override
    public LiveData<List<Tweet>> getTweets() {
        return mLocalDataSource.getTweets();
    }
    @Override
    public void insertTweet(final Tweet tweet) {

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLocalDataSource.insertTweet(tweet);

            }
        });
    }
}
