package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.*
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.domain.model.Link
import java.util.*

object LinkFactory {

    private val random = Random()

    fun getLinksFromRemote(response: EntityResponse): List<Link> {
        return mutableListOf<Link>().apply {
            addAll(getHashTags(response.hashTags))
            addAll(getUserMentions(response.userMentions))
            addAll(getUrls(response.urls))
        }
    }

    private fun getHashTags(responses: List<HashTagResponse>?): List<Link> {
        return responses?.map {
            createLink(it.text, it.indices, Link.Companion.LinkType.HASH_TAG)
        } ?: emptyList()
    }

    private fun getUserMentions(responses: List<UserMentionResponse>?): List<Link> {
        return responses?.map {
            createLink(it.screenName, it.indices, Link.Companion.LinkType.USER_MENTION)
        } ?: emptyList()
    }

    private fun getUrls(responses: List<UrlResponse>?): List<Link> {
        return responses?.map {
            createLink(it.url, it.indices, Link.Companion.LinkType.URL)
        } ?: emptyList()
    }

    private fun createLink(text: String, indices: List<Int>, type: Link.Companion.LinkType): Link {
        val id = random.nextInt()
        val range = IntRange(indices.first(), indices.last())
        return Link(id, text, range, type)
    }

    fun getLinksFromLocal(entities: List<LinkEntity>): List<Link> {
        return entities.map {
            Link(
                it.id,
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

}