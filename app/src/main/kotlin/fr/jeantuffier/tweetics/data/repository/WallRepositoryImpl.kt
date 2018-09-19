package fr.jeantuffier.tweetics.data.repository

import android.content.Context
import fr.jeantuffier.tweetics.data.datastore.wall.LocalWallDataStore
import fr.jeantuffier.tweetics.data.datastore.wall.RemoteWallDataStore
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Single

private const val WALL_PREFERENCES = "wall_preferences"
private const val WALL_TWEETS = "wall_tweets"

class WallRepositoryImpl(
    private val context: Context,
    private val localWallDataStore: LocalWallDataStore,
    private val remoteWallDataStore: RemoteWallDataStore
) : WallRepository {

    override fun getTweets(): Single<List<Tweet>> {
        return if (isMoreThanTenMinutesSinceLastUpdate()) {
            getRemoteTweets()
        } else {
            getLocalTweets()
        }
    }

    private fun isMoreThanTenMinutesSinceLastUpdate(): Boolean {
        val lastUpdate = getLastUpdate()
        return lastUpdate == 0L || System.currentTimeMillis() > (10 * 60 * 1000 + lastUpdate)
    }

    private fun getLastUpdate(): Long {
        return context
            .getSharedPreferences(WALL_PREFERENCES, Context.MODE_PRIVATE)
            .getLong(getPreferenceKey(), 0)
    }

    private fun getRemoteTweets(): Single<List<Tweet>> {
        return remoteWallDataStore
            .getTweets()
            .doOnSuccess { saveTweets(it) }
    }

    private fun saveTweets(tweets: List<Tweet>) {
        localWallDataStore.saveTweets(tweets)
        setLastUpdate()
    }

    private fun setLastUpdate() {
        context
            .getSharedPreferences(WALL_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(getPreferenceKey(), System.currentTimeMillis())
            .apply()
    }

    private fun getPreferenceKey() = "$WALL_TWEETS:${Config.WALL_SCREEN_NAME}"

    private fun getLocalTweets(): Single<List<Tweet>> {
        return localWallDataStore.getTweets()
    }

}
