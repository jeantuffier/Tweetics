package fr.jeantuffier.tweetics.politician.repository

import android.content.Context
import fr.jeantuffier.tweetics.common.model.politician.Politician
import fr.jeantuffier.tweetics.common.model.politician.PoliticianDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val POLITICIAN_PREFERENCES = "politician_preferences"
private const val POLITICIAN_UPDATE = "politician_update"

class PoliticianRepository @Inject constructor(
    private val context: Context,
    private val politicianService: PoliticianService,
    private val politicianDao: PoliticianDao
) {

    fun getPoliticians(): Observable<List<Politician>> {
        return getPoliticiansFromDatabase()
            .flatMap { politicians ->
                if (shouldLoadFromApi(getLastUpdate(), politicians)) {
                    getPoliticiansFromApi()
                } else {
                    Observable.just(politicians)
                }
            }
    }

    private fun getLastUpdate(): Long {
        return context
            .getSharedPreferences(POLITICIAN_PREFERENCES, Context.MODE_PRIVATE)
            .getLong(POLITICIAN_UPDATE, 0)
    }

    private fun getPoliticiansFromDatabase(): Observable<List<Politician>> {
        return politicianDao
            .getPoliticians()
            .toObservable()
    }

    private fun shouldLoadFromApi(timestamp: Long, politicians: List<Politician>) =
        isMoreThanTenMinutesSinceLastUpdate(timestamp) || politicians.isEmpty()

    private fun isMoreThanTenMinutesSinceLastUpdate(timeStamp: Long): Boolean {
        return (timeStamp - System.currentTimeMillis()) > 10 * 60 * 1000
    }

    private fun getPoliticiansFromApi(): Observable<List<Politician>> {
        return politicianService
            .getPoliticians()
            .doOnNext { savePoliticianInDatabase(it) }
    }

    private fun savePoliticianInDatabase(politicians: List<Politician>) {
        Observable.fromCallable { politicianDao.insertAll(politicians) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext { setLastUpdate() }
            .subscribe()
    }

    private fun setLastUpdate() {
        context
            .getSharedPreferences(POLITICIAN_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(POLITICIAN_UPDATE, System.currentTimeMillis())
            .apply()
    }

}
