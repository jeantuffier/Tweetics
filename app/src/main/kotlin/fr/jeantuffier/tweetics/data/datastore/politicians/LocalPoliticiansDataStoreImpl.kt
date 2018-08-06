package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.data.factory.PoliticiansFactory
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalPoliticiansDataStoreImpl(
    private val politicianDao: PoliticianDao,
    private val factory: PoliticiansFactory
) : LocalPoliticiansDataStore {

    override fun clearPoliticians() {
        politicianDao.clear()
    }

    override fun getPoliticians(): Maybe<List<Politician>> {
        return politicianDao
            .getPoliticians()
            .map { factory.getPoliticiansFromLocal(it) }
    }

    override fun savePoliticians(politicians: List<Politician>, doOnNext: () -> Unit) {
        Observable
            .fromCallable { factory.getPoliticianEntities(politicians) }
            .map { politicianDao.insertAll(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext { doOnNext() }
            .subscribe()
    }

}
