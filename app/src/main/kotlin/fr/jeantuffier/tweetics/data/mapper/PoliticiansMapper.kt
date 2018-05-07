package fr.jeantuffier.tweetics.data.mapper

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.model.Politician
import javax.inject.Inject

class PoliticiansMapper @Inject constructor() {

    fun entitiesToModel(entities: List<PoliticianEntity>): List<Politician> {
        return mutableListOf<Politician>().apply {
            entities.forEach { add(entityToModel(it)) }
        }
    }

    private fun entityToModel(data: PoliticianEntity): Politician {
        return Politician(
            data.id,
            data.group.split(" "),
            data.name,
            data.role,
            data.screenName
        )
    }

    fun modelsToEntities(model: List<Politician>): List<PoliticianEntity> {
        return mutableListOf<PoliticianEntity>().apply {
            model.forEach { add(modelToEntity(it)) }
        }
    }

    private fun modelToEntity(model: Politician): PoliticianEntity {
        return PoliticianEntity(
            model.id,
            model.screenName,
            model.name,
            model.role,
            model.groups.joinToString(" ")
        )
    }

    fun responsesToModels(responses: List<PoliticianResponse>): List<Politician> {
        return mutableListOf<Politician>().apply {
            responses.forEach { add(responseToModel(it)) }
        }
    }

    private fun responseToModel(response: PoliticianResponse): Politician {
        return Politician(
            response.id,
            response.group.split(" "),
            response.name,
            response.role,
            response.screenName
        )
    }

}
