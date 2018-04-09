package fr.jeantuffier.tweetics.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import fr.jeantuffier.tweetics.common.model.politician.Politician
import fr.jeantuffier.tweetics.common.model.politician.PoliticianDao
import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import fr.jeantuffier.tweetics.common.model.tweet.TweetDao
import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapper
import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapperDao

@Database(
    entities = [Politician::class, Tweet::class, TweetWrapper::class],
    version = 4,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun ministerDao(): PoliticianDao

    abstract fun tweetDao(): TweetDao

    abstract fun tweetWrapperDao(): TweetWrapperDao
}
