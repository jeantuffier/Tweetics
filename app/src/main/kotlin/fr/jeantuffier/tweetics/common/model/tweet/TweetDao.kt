package fr.jeantuffier.tweetics.common.model.tweet

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface TweetDao {

    @Query("SELECT * FROM Tweet WHERE screen_name LIKE :screenName")
    fun getTweets(screenName: String): Maybe<List<Tweet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweet: Tweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweets: List<Tweet>)
}
