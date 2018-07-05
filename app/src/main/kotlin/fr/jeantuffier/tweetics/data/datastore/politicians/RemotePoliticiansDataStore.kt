package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

interface RemotePoliticiansDataStore {
    fun getPoliticians(): Single<List<Politician>>
}