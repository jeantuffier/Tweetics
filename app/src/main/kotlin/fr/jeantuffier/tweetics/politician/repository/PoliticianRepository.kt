package fr.jeantuffier.tweetics.politician.repository

import fr.jeantuffier.tweetics.common.model.politician.Politician
import fr.jeantuffier.tweetics.common.model.politician.PoliticianDao
import fr.jeantuffier.tweetics.common.model.tableupdate.TableUpdate
import fr.jeantuffier.tweetics.common.model.tableupdate.TableUpdateDao
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val POLITICIAN = "Politician"

class PoliticianRepository @Inject constructor(
    private val politicianService: PoliticianService,
    private val politicianDao: PoliticianDao,
    private val tableUpdateDao: TableUpdateDao
) {

    fun getMinister(): Observable<List<Politician>> {
        return getTableUpdate()
            .zipWith(getPoliticiansFromDatabase(), zipWithTableUpdate())
            .flatMap { (tableUpdate, politicians) ->
                if (shouldLoadFromApi(tableUpdate, politicians)) {
                    getPoliticiansFromApi()
                } else {
                    Observable.just(politicians)
                }
            }
    }

    private fun getTableUpdate(): Observable<TableUpdate> {
        return tableUpdateDao
            .getTableUpdate(POLITICIAN)
            .switchIfEmpty(Maybe.just(TableUpdate.getDefault()))
            .toObservable()
    }

    private fun getPoliticiansFromDatabase(): Observable<List<Politician>> {
        return politicianDao
            .getPoliticians()
            .toObservable()
    }

    private fun zipWithTableUpdate(): BiFunction<TableUpdate, List<Politician>, Pair<TableUpdate, List<Politician>>> {
        return BiFunction { tableUpdate, politicians -> tableUpdate to politicians }
    }

    private fun shouldLoadFromApi(tableUpdate: TableUpdate, politicians: List<Politician>)
            = isMoreThanTenMinutesSinceLastUpdate(tableUpdate.timeStamp) || politicians.isEmpty()

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
            .subscribe()
    }

}
