package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.model.Politician

object PoliticianFactory {

    fun getPoliticiansFromLocal(entities: List<PoliticianEntity>): List<Politician> {
        return entities.map { getPoliticianFromLocal(it) }
    }

    fun getPoliticianFromLocal(entity: PoliticianEntity): Politician {
        return Politician(
            entity.id,
            entity.group.split(" "),
            entity.name,
            entity.pictureUrl,
            entity.role,
            entity.screenName
        )
    }

    fun getPoliticianEntities(models: List<Politician>): List<PoliticianEntity> {
        return models.map { getPoliticianEntity(it) }
    }

    fun getPoliticianEntity(politician: Politician): PoliticianEntity {
        return PoliticianEntity(
            politician.id,
            politician.groups.joinToString(" "),
            politician.name,
            politician.pictureUrl,
            politician.role,
            politician.screenName
        )
    }

    fun getPoliticiansFromResponses(responses: List<PoliticianResponse>): List<Politician> {
        return responses.map { getPoliticianFromResponse(it) }
    }

    fun getPoliticianFromResponse(response: PoliticianResponse): Politician {
        return Politician(
            response.id,
            response.group.split(" "),
            response.name,
            "",
            response.role,
            response.screenName
        )
    }

}
