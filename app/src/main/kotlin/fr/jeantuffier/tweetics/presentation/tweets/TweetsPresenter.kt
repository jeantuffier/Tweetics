package fr.jeantuffier.tweetics.presentation.tweets

import fr.jeantuffier.tweetics.domain.repositories.TweetsRepository
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TweetsPresenter(
    private val tweetRepository: TweetsRepository
) : TweetsContract.Presenter {

    private lateinit var view: TweetsContract.View

    override fun loadContent(screenName: String) {
        tweetRepository
            .getTweets(screenName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { tweets, throwable ->
                val viewState = if (throwable == null) {
                    TweetsViewState.Loaded(tweets)
                } else {
                    throwable.printStackTrace()
                    TweetsViewState.Error(throwable.message)
                }

                view.updateViewState(viewState)
            }
    }

    override fun getImageUrl(screenName: String) =
        "${Config.TWEETICS_SERVER_IMAGE}/$screenName.jpg"

    override fun setView(view: TweetsContract.View) {
        this.view = view
    }
}