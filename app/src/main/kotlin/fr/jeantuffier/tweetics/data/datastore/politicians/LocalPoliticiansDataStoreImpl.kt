package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.datastore.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.domain.mapper.BiDirectionalMapper
import fr.jeantuffier.tweetics.domain.mapper.Mapper
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalPoliticiansDataStoreImpl @Inject constructor(
    private val politicianDao: PoliticianDao,
    private val mapper: BiDirectionalMapper<PoliticianEntity, Politician>
) : LocalPoliticiansDataStore {

    override fun getPoliticians(): Single<List<Politician>> {
        return politicianDao
            .getPoliticians()
            .map { mapper.toRight(it) }
    }

    override fun savePoliticians(politicians: List<Politician>, doOnNext: () -> Unit) {
        Observable
            .fromCallable { mapper.toLeft(politicians) }
            .map { politicianDao.insertAll(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext { doOnNext() }
            .subscribe()
    }

}
