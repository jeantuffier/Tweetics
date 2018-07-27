package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

interface LocalPoliticiansDataStore {
    fun clearPoliticians()
    fun getPoliticians(): Single<List<Politician>>
    fun savePoliticians(politicians: List<Politician>, doOnNext: () -> Unit)
}