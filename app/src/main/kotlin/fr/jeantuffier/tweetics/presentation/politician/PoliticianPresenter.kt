package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.data.repository.PoliticiansRepository
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.Normalizer

class PoliticianPresenter(
    private val politiciansRepository: PoliticiansRepository
) : PoliticianContract.Presenter {

    private lateinit var view: PoliticianContract.View

    override fun loadContent() {
        politiciansRepository
            .getPoliticians()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { politicians -> sortList(politicians) }
            .subscribe(object : SingleObserver<List<Politician>> {
                override fun onSuccess(politicians: List<Politician>) {
                    val state = PoliticianViewState.Loaded(politicians)
                    view.updateViewState(state)
                }

                override fun onError(throwable: Throwable) {
                    val state = PoliticianViewState.Error()
                    view.updateViewState(state)
                }

                override fun onSubscribe(d: Disposable) = Unit
            })
    }

    private fun sortList(politicians: List<Politician>): List<Politician> {
        return politicians.sortedBy {
            Normalizer.normalize(it.name, Normalizer.Form.NFD)
        }
    }

    override fun attachView(view: PoliticianContract.View) {
        this.view = view
    }
}