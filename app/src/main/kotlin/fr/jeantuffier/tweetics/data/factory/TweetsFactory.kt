package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.*
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.*
import java.util.*

private const val VIDEO_CONTENT_TYPE = "application/x-mpegURL"

class TweetsFactory {

    fun getTweets(
        entities: List<TweetEntity>,
        screenName: String,
        links: List<Link>
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
                getUser(it.userId)
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
                it.user.id.toInt()
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
            getLinks(response),
            getMedias(
                response.entities?.media
                    ?.union(response.extendedEntities?.media ?: emptyList())?.toList()
            ),
            getDisplayTextRangeFromResponse(response.displayTextRange ?: listOf(0, 0)),
            getUser(response.user)
        )
    }

    private fun getLinks(response: TweetResponse): List<Link> {
        return mutableListOf<Link>().apply {
            addAll(getHashTags(response.entities?.hashTags))
            addAll(getUserMentions(response.entities?.userMentions))
            addAll(getUrls(response.entities?.urls))
        }
    }

    private fun getHashTags(responses: List<HashTagResponse>?): List<Link> {
        return responses?.map { createLink(it.text, it.indices, Link.Companion.LinkType.HASH_TAG) }
                ?: emptyList()
    }

    private fun getUserMentions(responses: List<UserMentionResponse>?): List<Link> {
        return responses?.map {
            createLink(
                it.screenName,
                it.indices,
                Link.Companion.LinkType.USER_MENTION
            )
        } ?: emptyList()
    }

    private fun getUrls(responses: List<UrlResponse>?): List<Link> {
        return responses?.map { createLink(it.url, it.indices, Link.Companion.LinkType.URL) }
                ?: emptyList()
    }

    private fun createLink(text: String, indices: List<Int>, type: Link.Companion.LinkType): Link {
        val id = UUID.randomUUID().toString()
        val range = IntRange(indices.first(), indices.last())
        return Link(id, text, range, type)
    }

    private fun getMedias(medias: List<MediaResponse>?): List<Media> {
        return medias?.map {
            Media(
                it.id,
                it.url,
                it.type,
                getSizes(it.sizes),
                it.videoInfo?.let {
                    it.variants.firstOrNull { it.contentType == VIDEO_CONTENT_TYPE }?.let {
                        VideoInfo(it.contentType, it.url)
                    }
                }
            )
        } ?: emptyList()
    }

    private fun getSizes(response: SizesResponse): List<Size> {
        return mutableListOf<Size>().apply {
            add(getSize(response.thumb, Size.Companion.Type.THUMB))
            add(getSize(response.small, Size.Companion.Type.SMALL))
            add(getSize(response.medium, Size.Companion.Type.MEDIUM))
            add(getSize(response.large, Size.Companion.Type.LARGE))
        }
    }

    private fun getSize(response: SizeResponse, type: Size.Companion.Type) =
        Size(UUID.randomUUID().toString(), response.width, response.height, response.resize, type)

    private fun getDisplayTextRangeFromResponse(range: List<Int>): IntRange {
        val start = range.first()
        val end = range.last()
        return IntRange(start, end)
    }

    private fun getUser(response: UserResponse?): User {
        return User(
            response?.idStr ?: "",
            response?.name ?: "",
            response?.picture ?: ""
        )
    }

    private fun getUser(userId: Int): User {
        return User("", "", "")
    }

}
