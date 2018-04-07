package fr.jeantuffier.tweetics.tweets.repository

import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import fr.jeantuffier.tweetics.common.model.tweet.TweetDao
import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapper
import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapperDao
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TweetRepository @Inject constructor(
    private val tweetService: TweetService,
    private val tweetDao: TweetDao,
    private val tweetWrapperDao: TweetWrapperDao
) {

    fun getTweetWrapper(screenName: String): Observable<TweetWrapper> {
        return getTweetWrapperFromDatabase(screenName)
            .flatMap { tweetWrapper ->
                if (shouldLoadFromApi(tweetWrapper)) {
                    getTweetWrapperFromApi(screenName)
                } else {
                    getTweetsFromDatabase(screenName)
                        .map {
                            tweetWrapper.tweets = it
                            tweetWrapper
                        }
                }
            }
    }

    private fun getTweetWrapperFromDatabase(screenName: String): Observable<TweetWrapper> {
        return tweetWrapperDao
            .getTweetWrapper(screenName)
            .switchIfEmpty(Maybe.just(TweetWrapper.getDefault()))
            .toObservable()
    }

    private fun shouldLoadFromApi(tweetWrapper: TweetWrapper) =
        tweetWrapper.lastUpdate.isEmpty() || isMoreThanTenMinutesSinceLastUpdate(tweetWrapper.lastUpdate)

    private fun isMoreThanTenMinutesSinceLastUpdate(lastUpdate: String): Boolean {
        val formatter = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatter.parse(lastUpdate)
        return (System.currentTimeMillis() - date.time) > 10 * 60 * 1000
    }

    private fun getTweetWrapperFromApi(screenName: String): Observable<TweetWrapper> {
        return tweetService
            .getTweetWrapper(screenName)
            .doOnNext {
                saveTweetWrapperInDatabase(it)
                saveTweetsInDatabase(screenName, it.tweets)
            }
    }

    private fun saveTweetWrapperInDatabase(tweetWrapper: TweetWrapper) {
        Observable.fromCallable { tweetWrapperDao.insert(tweetWrapper) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    private fun saveTweetsInDatabase(screenName: String, tweets: List<Tweet>?) {
        if (tweets != null && tweets.isNotEmpty()) {
            for (tweet in tweets) {
                tweet.screenName = screenName
            }

            Observable.fromCallable { tweetDao.insertAll(tweets) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun getTweetsFromDatabase(screenName: String): Observable<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .toObservable()
    }

}
