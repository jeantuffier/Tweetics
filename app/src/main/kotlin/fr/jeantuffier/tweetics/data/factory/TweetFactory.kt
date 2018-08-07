package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.EntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.ExtendedEntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User

object TweetFactory {

    fun mapToTweets(
        entities: List<TweetEntity>,
        links: List<Link>,
        user: User
    ): List<Tweet> {
        return entities.map {
            Tweet(
                it.id,
                it.createdAt,
                it.fullText,
                null,
                links,
                emptyList(),
                getDisplayTextRangeFromEntity(it.displayTextRange),
                user
            )
        }
    }

    private fun getDisplayTextRangeFromEntity(range: String): IntRange {
        val list = range.split("..")
        return IntRange(list.first().toInt(), list.last().toInt())
    }

    fun mapToTweetEntities(
        models: List<Tweet>
    ): List<TweetEntity> {
        return models.map {
            TweetEntity(
                it.id,
                it.user.id,
                it.createdAt,
                it.fullText,
                it.reTweet?.id ?: "",
                it.displayTextRange.toString()
            )
        }
    }

    fun mapToTweets(
        responses: List<TweetResponse>,
        tweetId: Int
    ): List<Tweet> {
        return responses.map {
            val links = getLinks(tweetId, it.entities)
            val medias = getMedias(it.extendedEntities)
            val user = UserFactory.mapToUser(it.user)
            getTweet(it, links, medias, user)
        }
    }

    private fun getLinks(tweetId: Int, entityResponse: EntityResponse?): List<Link> {
        return entityResponse?.let { LinkFactory.mapToLinks(tweetId, it) } ?: emptyList()
    }

    private fun getMedias(extendedEntityResponse: ExtendedEntityResponse?): List<Media> {
        return extendedEntityResponse?.let { MediaFactory.getMedias(it.media) } ?: emptyList()
    }

    private fun getTweet(
        response: TweetResponse,
        links: List<Link>,
        medias: List<Media>,
        user: User
    ): Tweet {
        val reTweet = if (response.retweetedStatus?.idStr != null) {
            getTweet(response.retweetedStatus, links, medias, user)
        } else null

        return Tweet(
            response.idStr ?: "",
            response.createdAt ?: "",
            response.fullText ?: "",
            reTweet,
            links,
            medias,
            getDisplayTextRangeFromResponse(response.displayTextRange),
            user
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
