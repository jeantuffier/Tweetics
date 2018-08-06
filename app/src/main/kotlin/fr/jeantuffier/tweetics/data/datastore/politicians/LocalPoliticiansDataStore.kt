package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Maybe

interface LocalPoliticiansDataStore {
    fun clearPoliticians()
    fun getPoliticians(): Maybe<List<Politician>>
    fun savePoliticians(politicians: List<Politician>, doOnNext: () -> Unit)
}