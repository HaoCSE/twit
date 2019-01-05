package code.hao.twit.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import code.hao.twit.data.local.model.Tweet;

import java.util.List;

@Dao
public interface TweetsDao {
    @Query("SELECT * FROM tweet")
    LiveData<List<Tweet>> getTweets();

    @Insert
    void insertTweet(Tweet tweet);
}
