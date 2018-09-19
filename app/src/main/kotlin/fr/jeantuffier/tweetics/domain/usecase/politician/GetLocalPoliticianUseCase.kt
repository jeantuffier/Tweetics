package fr.jeantuffier.tweetics.domain.usecase.politician

import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

class GetLocalPoliticianUseCase(private val politicianDao: PoliticianDao) {

    fun get(politicianId: String): Single<Politician?> {
        return politicianDao.getPolitician(politicianId)
            .toSingle()
            .map { PoliticianFactory.mapEntityToPolitician(it) }
    }

}
