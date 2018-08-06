package fr.jeantuffier.tweetics.presentation.wall

import fr.jeantuffier.tweetics.data.repository.WallRepository
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
            .subscribe(object: MaybeObserver<List<Tweet>> {
                override fun onSuccess(tweets: List<Tweet>) {
                    updateViewState(WallViewState.Loaded(tweets))
                }

                override fun onError(throwable: Throwable) {
                    throwable.printStackTrace()
                    updateViewState(WallViewState.Error(throwable.message))
                }

                override fun onComplete() = Unit
                override fun onSubscribe(d: Disposable) = Unit
            })
    }

    private fun updateViewState(viewState: WallViewState) = view?.updateViewState(viewState)

}
