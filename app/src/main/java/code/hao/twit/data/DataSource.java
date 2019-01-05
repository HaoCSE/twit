package code.hao.twit.data;

import androidx.lifecycle.LiveData;
import code.hao.twit.data.local.model.Tweet;
import java.util.List;

public interface DataSource {
    LiveData<List<Tweet>> getTweets();

    void insertTweet(Tweet tweet);
}
