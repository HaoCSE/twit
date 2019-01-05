package code.hao.twit.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import code.hao.twit.data.local.dao.TweetsDao;
import code.hao.twit.data.local.model.Tweet;

@Database(
        entities = {Tweet.class},
        version = 1,
        exportSchema = false)
public abstract class TweetDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Tweets.db";

    private static final Object sLock = new Object();
    private static TweetDatabase INSTANCE;

    public static TweetDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static TweetDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                TweetDatabase.class,
                DATABASE_NAME).build();
    }

    public abstract TweetsDao tweetsDao();
}
