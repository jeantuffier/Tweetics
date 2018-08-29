package fr.jeantuffier.tweetics.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import io.reactivex.Maybe

@Dao
interface TweetDao {

    @Query("SELECT * FROM Tweet WHERE politician_id LIKE :politicianId")
    fun getTweets(politicianId: String): Maybe<List<TweetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweet: TweetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweets: List<TweetEntity>)
}
