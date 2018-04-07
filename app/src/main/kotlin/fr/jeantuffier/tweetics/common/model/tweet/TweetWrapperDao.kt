package fr.jeantuffier.tweetics.common.model.tweet

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface TweetWrapperDao {

    @Query("SELECT * FROM TweetWrapper WHERE screen_name LIKE :screenName")
    fun getTweetWrapper(screenName: String): Maybe<TweetWrapper>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweetWrapper: TweetWrapper)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweetWrapper: List<TweetWrapper>)
}
