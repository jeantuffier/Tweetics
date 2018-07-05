package fr.jeantuffier.tweetics.data.repository

import android.content.Context
import fr.jeantuffier.tweetics.data.datastore.tweet.LocalTweetsDataStore
import fr.jeantuffier.tweetics.data.datastore.tweet.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

private const val TWEETS_PREFERENCES = "tweets_preferences"
private const val POLITICIAN_TWEETS = "politician_tweets"

class TweetsRepositoryImpl(
    private val context: Context,
    private val localTweetsDataStore: LocalTweetsDataStore,
    private val remoteTweetsDataStore: RemoteTweetsDataStore
) : TweetsRepository {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return getRemoteTweets(screenName)
        /*return localTweetsDataStore.getTweets(screenName)
            .flatMap { tweets ->
                if (shouldLoadFromApi(tweets.size, screenName)) {
                    getRemoteTweets(screenName)
                } else {
                    Single.just(tweets)
                }
            }*/
    }

    private fun shouldLoadFromApi(listSize: Int, screenName: String) =
        listSize == 0 || isMoreThanTenMinutesSinceLastUpdate(screenName)

    private fun isMoreThanTenMinutesSinceLastUpdate(screeName: String): Boolean {
        return getLastUpdate(screeName) > 10 * 60 * 1000
    }

    private fun getLastUpdate(screenName: String): Long {
        return context
            .getSharedPreferences(TWEETS_PREFERENCES, Context.MODE_PRIVATE)
            .getLong(getPreferenceKey(screenName), 0)
    }

    private fun getPreferenceKey(screenName: String) = "$POLITICIAN_TWEETS:$screenName"

    private fun getRemoteTweets(screenName: String): Single<List<Tweet>> {
        return remoteTweetsDataStore
            .getTweets(screenName)
        //.doOnSuccess { saveTweets(screenName, it) }
    }

    private fun saveTweets(screenName: String, tweets: List<Tweet>, politicianId: String) {
        localTweetsDataStore
            .saveTweets(screenName, tweets, politicianId) {
                setLastUpdate(screenName)
            }
    }

    private fun setLastUpdate(screenName: String) {
        context
            .getSharedPreferences(TWEETS_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(getPreferenceKey(screenName), System.currentTimeMillis())
            .apply()
    }

}