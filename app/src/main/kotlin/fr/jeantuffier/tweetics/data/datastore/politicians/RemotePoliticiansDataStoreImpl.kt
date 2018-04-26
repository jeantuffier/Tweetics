package fr.jeantuffier.tweetics.data.datastore.politicians

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.data.retrofit.service.PoliticianService
import fr.jeantuffier.tweetics.domain.datastore.RemotePoliticiansDataStore
import fr.jeantuffier.tweetics.domain.mapper.Mapper
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single
import javax.inject.Inject

class RemotePoliticiansDataStoreImpl @Inject constructor(
    private val politicianService: PoliticianService,
    private val politicianMapper: Mapper<PoliticianResponse, Politician>
) : RemotePoliticiansDataStore {

    override fun getPoliticians(): Single<List<Politician>> {
        return politicianService
            .getPoliticians()
            .map { politicianMapper.toRight(it) }
    }

}
