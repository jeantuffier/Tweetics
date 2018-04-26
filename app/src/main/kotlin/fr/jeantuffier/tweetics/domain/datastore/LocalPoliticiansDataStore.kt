package fr.jeantuffier.tweetics.domain.datastore

import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

interface LocalPoliticiansDataStore {
    fun getPoliticians(): Single<List<Politician>>
    fun savePoliticians(politicians: List<Politician>, doOnNext: () -> Unit)
}