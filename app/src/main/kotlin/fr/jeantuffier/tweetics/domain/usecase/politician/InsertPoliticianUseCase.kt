package fr.jeantuffier.tweetics.domain.usecase.politician

import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.domain.model.Politician

class InsertPoliticianUseCase(private val politicianDao: PoliticianDao) {

    fun insert(politicians: List<Politician>) {
        val entities = PoliticianFactory.mapPoliticiansToEntities(politicians)
        politicianDao.insertAll(entities)
    }

    fun insert(politician: Politician) {
        val entity = PoliticianFactory.mapPoliticianToEntity(politician)
        politicianDao.insert(entity)
    }

}
