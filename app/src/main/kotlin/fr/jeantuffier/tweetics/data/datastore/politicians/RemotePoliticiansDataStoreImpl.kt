package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.data.factory.PoliticiansFactory
import fr.jeantuffier.tweetics.data.retrofit.service.PoliticianService
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

class RemotePoliticiansDataStoreImpl(
    private val politicianService: PoliticianService,
    private val factory: PoliticiansFactory
) : RemotePoliticiansDataStore {

    override fun getPoliticians(): Single<List<Politician>> {
        return politicianService
            .getPoliticians()
            .map { factory.getPoliticiansFromResponses(it) }
    }

}
