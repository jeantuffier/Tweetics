package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.repositories.PoliticiansRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.Normalizer
import javax.inject.Inject

class PoliticianPresenter @Inject constructor(
    private val politiciansRepository: PoliticiansRepository,
    private val view: PoliticianContract.View
) : PoliticianContract.Presenter {

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

}