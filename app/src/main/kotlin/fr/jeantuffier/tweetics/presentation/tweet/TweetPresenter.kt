package fr.jeantuffier.tweetics.presentation.tweet

import fr.jeantuffier.tweetics.data.repository.TweetsRepository
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TweetPresenter(
    private val tweetRepository: TweetsRepository
) : TweetContract.Presenter {

    private lateinit var view: TweetContract.View

    override fun loadContent(screenName: String) {
        tweetRepository
            .getTweets(screenName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { tweets, throwable ->
                val viewState = if (throwable == null) {
                    TweetViewState.Loaded(tweets)
                } else {
                    throwable.printStackTrace()
                    TweetViewState.Error(throwable.message)
                }

                view.updateViewState(viewState)
            }
    }

    override fun getImageUrl(screenName: String) =
        "${Config.TWEETICS_SERVER_IMAGE}/$screenName.jpg"

    override fun setView(view: TweetContract.View) {
        this.view = view
    }
}