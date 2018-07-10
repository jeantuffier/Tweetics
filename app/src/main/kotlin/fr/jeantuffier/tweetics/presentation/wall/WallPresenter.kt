package fr.jeantuffier.tweetics.presentation.wall

import fr.jeantuffier.tweetics.data.repository.WallRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WallPresenter(
    private val wallRepository: WallRepository
) : WallContract.Presenter {

    private var view: WallContract.View? = null

    override fun attach(view: WallContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun loadContent() {
        wallRepository.getTweets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { tweets, throwable ->
                val viewState = if (throwable == null) {
                    WallViewState.Loaded(tweets)
                } else {
                    throwable.printStackTrace()
                    WallViewState.Error(throwable.message)
                }

                view?.updateViewState(viewState)
            }
    }

}
