package fr.jeantuffier.tweetics.data.mapper

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.link.EntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.link.HashTagResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.link.UrlResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.link.UserMentionResponse
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import java.util.*
import javax.inject.Inject

class TweetsFactory @Inject constructor() {

    fun getTweets(
        entities: List<TweetEntity>,
        screenName: String,
        hashTags: List<Link.HashTag>,
        userMentions: List<Link.UserMention>,
        urls: List<Link.Url>,
        media: List<Link.Url>
    ): List<Tweet> {
        return entities.map {
            Tweet(
                it.id,
                screenName,
                it.createdAt,
                it.fullText,
                null,
                hashTags,
                userMentions,
                urls,
                media,
                getDisplayTextRangeFromEntity(it.displayTextRange)
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
                it.displayTextRange.toString()
            )
        }
    }

    fun getTweets(responses: List<TweetResponse>, screenName: String): List<Tweet> {
        return responses.map {
            getTweet(it, screenName)
        }
    }

    private fun getTweet(
        response: TweetResponse,
        screenName: String
    ): Tweet {
        val reTweet = if (response.retweetedStatus?.idStr != null) {
             getTweet(response.retweetedStatus, screenName)
        } else null

        return Tweet(
            response.idStr ?: "",
            screenName,
            response.createdAt ?: "",
            response.fullText ?: "",
            reTweet,
            response.entities?.hashTags?.map(getHashTagFactory()),
            response.entities?.userMentions?.map(getUserMentionFactory()),
            response.entities?.urls?.map(getUrlFactory()),
            response.entities?.media?.map(getUrlFactory()),
            getDisplayTextRangeFromResponse(response.displayTextRange ?: listOf(0, 0))
        )
    }

    private fun getDisplayTextRangeFromResponse(range: List<Int>): IntRange {
        val start = range.first()
        val end = range.last()
        return IntRange(start, end)
    }

    private fun getHashTagFactory(): (response: HashTagResponse) -> Link.HashTag {
        return {
            Link.HashTag(
                UUID.randomUUID().toString(),
                it.text,
                IntRange(it.indices.first(), it.indices.last())
            )
        }
    }

    private fun getUserMentionFactory(): (response: UserMentionResponse) -> Link.UserMention {
        return {
            Link.UserMention(
                UUID.randomUUID().toString(),
                it.screenName,
                IntRange(it.indices.first(), it.indices.last())
            )
        }
    }

    private fun getUrlFactory(): (response: UrlResponse) -> Link.Url {
        return { getUrl(it) }
    }

    private fun getUrl(response: UrlResponse): Link.Url {
        return Link.Url(
            UUID.randomUUID().toString(),
            response.url,
            IntRange(response.indices.first(), response.indices.last())
        )
    }

}
