package fr.jeantuffier.tweetics.data.mapper

import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.domain.mapper.BiDirectionalMapper
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet

class LocalPoliticianMapper : BiDirectionalMapper<PoliticianEntity, Politician> {

    var tweets: List<Tweet> = emptyList()

    override fun toRight(data: List<PoliticianEntity>): List<Politician> {
        return mutableListOf<Politician>().apply {
            data.forEach { add(toRight(it)) }
        }
    }

    override fun toRight(data: PoliticianEntity): Politician {
        return Politician(
            data.id,
            data.group.split(" "),
            data.name,
            data.role,
            data.screenName,
            tweets
        )
    }

    override fun toLeft(data: List<Politician>): List<PoliticianEntity> {
        return mutableListOf<PoliticianEntity>().apply {
            data.forEach { add(toLeft(it)) }
        }
    }

    override fun toLeft(data: Politician): PoliticianEntity {
        return PoliticianEntity(
            data.id,
            data.screenName,
            data.name,
            data.role,
            data.groups.joinToString(" ")
        )
    }

}