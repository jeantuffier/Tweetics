package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.EntityResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.HashTagResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.UrlResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.UserMentionResponse
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.domain.model.Link
import java.util.*

object LinkFactory {

    private val random = Random()

    fun mapToLinks(tweetId: Int, response: EntityResponse): List<Link> {
        return mutableListOf<Link>().apply {
            addAll(getHashTags(tweetId, response.hashTags))
            addAll(getUserMentions(tweetId, response.userMentions))
            addAll(getUrls(tweetId, response.urls))
        }
    }

    private fun getHashTags(tweetId: Int, responses: List<HashTagResponse>?): List<Link> {
        return responses?.map {
            createLink(tweetId, it.text, it.indices, Link.Companion.LinkType.HASH_TAG)
        } ?: emptyList()
    }

    private fun getUserMentions(tweetId: Int, responses: List<UserMentionResponse>?): List<Link> {
        return responses?.map {
            createLink(tweetId, it.screenName, it.indices, Link.Companion.LinkType.USER_MENTION)
        } ?: emptyList()
    }

    private fun getUrls(tweetId: Int, responses: List<UrlResponse>?): List<Link> {
        return responses?.map {
            createLink(tweetId, it.url, it.indices, Link.Companion.LinkType.URL)
        } ?: emptyList()
    }

    private fun createLink(
        tweetId: Int,
        text: String,
        indices: List<Int>,
        type: Link.Companion.LinkType
    ): Link {
        val id = random.nextInt()
        val range = IntRange(indices.first(), indices.last())
        return Link(id, tweetId, text, range, type)
    }

    fun mapToLinks(tweetId: Int, entities: List<LinkEntity>): List<Link> {
        return entities.map {
            Link(
                it.id,
                tweetId,
                it.text,
                getIndices(it.indices),
                Link.Companion.LinkType.valueOf(it.linkType)
            )
        }
    }

    private fun getIndices(stringIndices: String): IntRange {
        val values = stringIndices.split(",")
        return IntRange(values[0].toInt(), values[1].toInt())
    }

    fun mapToEntities(links: List<Link>): List<LinkEntity> {
        return links.map {
            LinkEntity(
                it.id,
                it.tweetId,
                it.text,
                it.indices.toString(),
                it.type.toString()
            )
        }
    }

}