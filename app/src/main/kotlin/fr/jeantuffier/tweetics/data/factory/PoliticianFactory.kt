package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.model.Politician

object PoliticianFactory {

    fun mapEntitiesToPoliticians(entities: List<PoliticianEntity>): List<Politician> {
        return entities.map { mapEntityToPolitician(it) }
    }

    fun mapEntityToPolitician(entity: PoliticianEntity): Politician {
        return Politician(
            entity.id,
            entity.description,
            entity.image,
            entity.name,
            entity.screenName
        )
    }

    fun mapPoliticiansToEntities(models: List<Politician>): List<PoliticianEntity> {
        return models.map { mapPoliticianToEntity(it) }
    }

    fun mapPoliticianToEntity(politician: Politician): PoliticianEntity {
        return PoliticianEntity(
            politician.id,
            politician.description,
            politician.image,
            politician.name,
            politician.screenName
        )
    }

    fun mapResponsesToPoliticians(responses: List<PoliticianResponse>): List<Politician> {
        return responses.map { mapResponseToPolitician(it) }
    }

    fun mapResponseToPolitician(response: PoliticianResponse): Politician {
        return Politician(
            response.id,
            response.description,
            response.image,
            response.name,
            response.screenName
        )
    }

}
