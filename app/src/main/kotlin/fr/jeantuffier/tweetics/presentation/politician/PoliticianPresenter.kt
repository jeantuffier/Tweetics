package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.data.repository.PoliticiansRepository
import io.reactivex.android.schedulers.AndroidSchedulers
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
            .subscribe { politicians, throwable ->
                val state = if (throwable == null) {
                    PoliticianViewState.Loaded(politicians)
                } else {
                    PoliticianViewState.Error(throwable.message)
                }
                view.updateViewState(state)
            }
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