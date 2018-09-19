package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.EntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.ExtendedEntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet

object TweetFactory {

    fun mapToTweets(
        entities: List<TweetEntity>,
        politician: Politician,
        links: List<Link>
    ): List<Tweet> {
        return entities.map { mapToTweet(it, politician, links)}
    }

    fun mapToTweet(
        entity: TweetEntity,
        politician: Politician,
        links: List<Link>
    ) : Tweet {
        return Tweet(
            entity.id,
            entity.createdAt,
            entity.fullText,
            null,
            links,
            emptyList(),
            getDisplayTextRangeFromEntity(entity.displayTextRange),
            politician
        )
    }

    private fun getDisplayTextRangeFromEntity(range: String): IntRange {
        val list = range.split("..")
        return IntRange(list.first().toInt(), list.last().toInt())
    }

    fun mapToEntities(
        models: List<Tweet>
    ): List<TweetEntity> {
        return models.map { mapToEntity(it) }
    }

    fun mapToEntity(tweet: Tweet): TweetEntity {
        return TweetEntity(
            tweet.id,
            tweet.politician.id,
            tweet.createdAt,
            tweet.fullText,
            tweet.reTweet?.id ?: "",
            tweet.displayTextRange.toString()
        )
    }

    fun mapToTweets(responses: List<TweetResponse>): List<Tweet> {
        return responses.map {
            val links = getLinks(it.id, it.entities)
            val medias = getMedias(it.extendedEntities)
            val user = PoliticianFactory.mapResponseToPolitician(it.politician)
            getTweet(it, links, medias, user)
        }
    }

    private fun getLinks(tweetId: String?, entityResponse: EntityResponse?): List<Link> {
        return if (tweetId != null && entityResponse != null) {
            LinkFactory.mapToLinks(tweetId, entityResponse)
        } else emptyList()
    }

    private fun getMedias(extendedEntityResponse: ExtendedEntityResponse?): List<Media> {
        return extendedEntityResponse?.let { MediaFactory.getMedias(it.media) } ?: emptyList()
    }

    private fun getTweet(
        response: TweetResponse,
        links: List<Link>,
        medias: List<Media>,
        politician: Politician
    ): Tweet {
        val reTweet = if (response.retweetedStatus?.id != null) {
            getTweet(response.retweetedStatus, links, medias, politician)
        } else null

        return Tweet(
            response.id ?: "",
            response.createdAt ?: "",
            response.fullText ?: "",
            reTweet,
            links,
            medias,
            getDisplayTextRangeFromResponse(response.displayTextRange),
            politician
        )
    }

    private fun getDisplayTextRangeFromResponse(range: List<Int>?): IntRange {
        if (range == null) {
            return IntRange(0, 0)
        }

        val start = range.first()
        val end = range.last()
        return IntRange(start, end)
    }

}
