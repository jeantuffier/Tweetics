package fr.jeantuffier.tweetics.data.mapper

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Tweet
import javax.inject.Inject

class TweetsMapper @Inject constructor() {

    fun entitiesToModels(entities: List<TweetEntity>): List<Tweet> {
        return mutableListOf<Tweet>().apply {
            entities.forEach { add(entityToModel(it)) }
        }
    }

    private fun entityToModel(entity: TweetEntity): Tweet {
        return Tweet(
            entity.id,
            entity.politicianId,
            entity.createdAt,
            entity.fullText,
            entity.retweetId
        )
    }

    fun modelsToEntities(models: List<Tweet>, screenName: String): List<TweetEntity> {
        return mutableListOf<TweetEntity>().apply {
            models.forEach { add(modelToEntity(it, screenName)) }
        }
    }

    private fun modelToEntity(model: Tweet, screenName: String): TweetEntity {
        return TweetEntity(
            model.id,
            model.politicianId,
            model.createdAt,
            model.fullText,
            screenName,
            model.retweetId
        )
    }

    fun responsesToModels(responses: List<TweetResponse>, screenName: String): List<Tweet> {
        return mutableListOf<Tweet>().apply {
            responses.forEach { add(responseToModel(it, screenName)) }
        }
    }

    private fun responseToModel(response: TweetResponse, screenName: String): Tweet {
        return Tweet(
            response.idStr,
            "",
            response.createdAt,
            response.fullText,
            response.retweetedStatus?.idStr ?: ""
        )
    }

}