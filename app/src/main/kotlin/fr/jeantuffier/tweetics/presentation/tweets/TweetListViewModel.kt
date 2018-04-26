package fr.jeantuffier.tweetics.presentation.tweets

import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.data.repository.TweetsRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class TweetListViewModel @Inject constructor(private val tweetRepository: TweetsRepositoryImpl) {

    fun loadContent(screenName: String) {
        tweetRepository
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