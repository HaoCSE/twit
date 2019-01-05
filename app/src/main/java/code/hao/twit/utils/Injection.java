package code.hao.twit.utils;

import android.content.Context;
import code.hao.twit.data.TweetRepository;
import code.hao.twit.data.local.TweetDatabase;
import code.hao.twit.data.local.TweetLocalDataSource;

public class Injection {

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static TweetLocalDataSource provideTweetsLocalDataSource(Context context) {
        TweetDatabase database = TweetDatabase.getInstance(context.getApplicationContext());
        return TweetLocalDataSource.getInstance(database);
    }
    /**
     * Creates an instance of provideTweetRepository
     */
    public static TweetRepository provideTweetRepository(Context context) {
        TweetLocalDataSource localDataSource = provideTweetsLocalDataSource(context);
        AppExecutors executors = AppExecutors.getInstance();
        return TweetRepository.getInstance(
                localDataSource,
                executors);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TweetRepository repository = provideTweetRepository(context);
        return ViewModelFactory.getInstance(repository);
    }
}

