package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.model.Politician
import javax.inject.Inject

class PoliticiansFactory @Inject constructor() {

    fun getPoliticiansFromEntities(entities: List<PoliticianEntity>): List<Politician> {
        return entities.map {
            Politician(
                it.id,
                it.group.split(" "),
                it.name,
                it.role,
                it.screenName
            )
        }
    }

    fun getPoliticianEntities(models: List<Politician>): List<PoliticianEntity> {
        return models.map {
            PoliticianEntity(
                it.id,
                it.screenName,
                it.name,
                it.role,
                it.groups.joinToString(" ")
            )
        }
    }

    fun getPoliticiansFromResponses(responses: List<PoliticianResponse>): List<Politician> {
        return responses.map {
            Politician(
                it.id,
                it.group.split(" "),
                it.name,
                it.role,
                it.screenName
            )
        }
    }

}
