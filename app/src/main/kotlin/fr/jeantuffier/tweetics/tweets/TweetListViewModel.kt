package fr.jeantuffier.tweetics.tweets

import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import fr.jeantuffier.tweetics.tweets.repository.TweetRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class TweetListViewModel @Inject constructor(private val tweetRepository: TweetRepository) {

    fun loadContent(screenName: String): Observable<List<Tweet>> {
        return tweetRepository
            .getTweets(screenName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getErrorMessage(throwable: Throwable): Int {
        return when (throwable) {
            is UnknownHostException -> R.string.network_error
            else -> R.string.error
        }
    }
}