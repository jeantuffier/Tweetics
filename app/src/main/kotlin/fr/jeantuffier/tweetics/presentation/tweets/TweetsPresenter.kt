package fr.jeantuffier.tweetics.presentation.tweets

import fr.jeantuffier.tweetics.domain.repositories.TweetsRepository
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TweetsPresenter @Inject constructor(
    private val tweetRepository: TweetsRepository,
    private val view: TweetsContract.View
) : TweetsContract.Presenter {

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

}