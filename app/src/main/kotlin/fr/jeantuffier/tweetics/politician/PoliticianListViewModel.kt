package fr.jeantuffier.tweetics.politician

import android.util.Log
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.model.politician.Politician
import fr.jeantuffier.tweetics.politician.repository.PoliticianRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.text.Normalizer
import javax.inject.Inject

class PoliticianListViewModel @Inject constructor(private val politicianRepository: PoliticianRepository) {

    fun loadContent(): Observable<List<Politician>> {
        return politicianRepository
            .getMinister()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { politicians -> sortList(politicians) }
    }

    private fun sortList(politicians: List<Politician>): List<Politician> {
        return politicians.sortedBy {
            Normalizer.normalize(it.name, Normalizer.Form.NFD)
        }
    }

    fun getErrorMessage(throwable: Throwable): Int {
        Log.e("ERROR", throwable.message)
        return when (throwable) {
            is UnknownHostException -> R.string.network_error
            else -> R.string.error
        }
    }

}