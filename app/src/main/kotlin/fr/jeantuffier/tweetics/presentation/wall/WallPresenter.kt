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
            .subscribe { tweets ->
                if (tweets.isNotEmpty()) {
                    updateViewState(WallViewState.Loaded(tweets))
                }
            }
    }

    private fun showError(throwable: Throwable) {
        throwable.printStackTrace()
        updateViewState(WallViewState.Error(throwable.message))
    }

    private fun updateViewState(viewState: WallViewState) = view?.updateViewState(viewState)

}
