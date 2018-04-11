package fr.jeantuffier.tweetics.tweets.repository

import android.content.Context
import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import fr.jeantuffier.tweetics.common.model.tweet.TweetDao
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TWEETS_PREFERENCES = "tweets_preferences"
private const val POLITICIAN_TWEETS = "politician_tweets"

class TweetRepository @Inject constructor(
    private val context: Context,
    private val tweetService: TweetService,
    private val tweetDao: TweetDao
) {

    fun getTweets(screenName: String): Observable<List<Tweet>> {
        return getTweetsFromDatabase(screenName)
            .flatMap { tweets ->
                if (shouldLoadFromApi(tweets.size, screenName)) {
                    getTweetsFromApi(screenName)
                } else {
                    getTweetsFromDatabase(screenName)
                }
            }
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

    private fun getTweetsFromApi(screenName: String): Observable<List<Tweet>> {
        return tweetService
            .getTweets(screenName)
            .doOnNext { saveTweetsInDatabase(screenName, it) }
    }

    private fun saveTweetsInDatabase(screenName: String, tweets: List<Tweet>?) {
        if (tweets != null && tweets.isNotEmpty()) {
            for (tweet in tweets) {
                tweet.screenName = screenName
            }

            Observable.fromCallable { tweetDao.insertAll(tweets) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { setLastUpdate(screenName) }
                .subscribe()
        }
    }

    private fun setLastUpdate(screenName: String) {
        context
            .getSharedPreferences(TWEETS_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(getPreferenceKey(screenName), System.currentTimeMillis())
            .apply()
    }

    private fun getPreferenceKey(screenName: String) = "$POLITICIAN_TWEETS:$screenName"

    private fun getTweetsFromDatabase(screenName: String): Observable<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .toObservable()
    }

}
