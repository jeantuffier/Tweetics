package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.data.factory.PoliticiansFactory
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.domain.datastore.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalPoliticiansDataStoreImpl(
    private val politicianDao: PoliticianDao,
    private val factory: PoliticiansFactory
) : LocalPoliticiansDataStore {

    override fun getPoliticians(): Single<List<Politician>> {
        return politicianDao
            .getPoliticians()
            .map { factory.getPoliticiansFromEntities(it) }
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
