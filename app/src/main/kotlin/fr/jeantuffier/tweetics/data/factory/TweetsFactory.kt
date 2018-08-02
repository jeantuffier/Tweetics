package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User

class TweetsFactory {

    fun getTweetsFromLocal(
        entities: List<TweetEntity>,
        screenName: String,
        links: List<Link>,
        user: User
    ): List<Tweet> {
        return entities.map {
            Tweet(
                it.id,
                screenName,
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

    fun getTweetEntities(
        models: List<Tweet>,
        screenName: String,
        politicianId: String
    ): List<TweetEntity> {
        return models.map {
            TweetEntity(
                it.id,
                politicianId,
                it.createdAt,
                it.fullText,
                screenName,
                it.reTweet?.id ?: "",
                it.displayTextRange.toString(),
                it.user.id
            )
        }
    }

    fun getTweetsFromRemote(
        responses: List<TweetResponse>,
        screenName: String
    ): List<Tweet> {
        return responses.map {
            val links = it.entities?.let { LinkFactory.getLinksFromRemote(it) } ?: emptyList()
            val medias = it.extendedEntities?.let { MediaFactory.getMedias(it.media) }
                    ?: emptyList()
            val user = UserFactory.getUserFromRemote(it.user)
            getTweet(it, screenName, links, medias, user)
        }
    }

    private fun getTweet(
        response: TweetResponse,
        screenName: String,
        links: List<Link>,
        medias: List<Media>,
        user: User
    ): Tweet {
        val reTweet = if (response.retweetedStatus?.idStr != null) {
            getTweet(response.retweetedStatus, screenName, links, medias, user)
        } else null

        return Tweet(
            response.idStr ?: "",
            screenName,
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
