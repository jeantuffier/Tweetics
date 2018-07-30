package fr.jeantuffier.tweetics.data.repository

import android.content.Context
import fr.jeantuffier.tweetics.data.datastore.wall.LocalWallDataStore
import fr.jeantuffier.tweetics.data.datastore.wall.RemoteWallDataStore
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Observable
import io.reactivex.Single

private const val WALL_PREFERENCES = "wall_preferences"
private const val WALL_TWEETS = "wall_tweets"

class WallRepositoryImpl(
    private val context: Context,
    private val localWallDataStore: LocalWallDataStore,
    private val remoteWallDataStore: RemoteWallDataStore
) : WallRepository {

    override fun getTweets(): Single<List<Tweet>> {
        return localWallDataStore.getLinks()
            .flatMap { localWallDataStore.getTweets(it) }
            .map { tweets ->
                if (shouldLoadFromApi(tweets.size)) {
                    getRemoteTweets()
                } else {
                    tweets
                }
            }
    }

    private fun shouldLoadFromApi(listSize: Int) =
        listSize == 0 || isMoreThanTenMinutesSinceLastUpdate()

    private fun isMoreThanTenMinutesSinceLastUpdate(): Boolean {
        return getLastUpdate() > 10 * 60 * 1000
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
        localWallDataStore
            .saveTweets(tweets) {
                setLastUpdate()
            }
    }

    private fun setLastUpdate() {
        context
            .getSharedPreferences(WALL_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(getPreferenceKey(), System.currentTimeMillis())
            .apply()
    }

    private fun getPreferenceKey() = "$WALL_TWEETS:${Config.WALL_SCREEN_NAME}"

}
