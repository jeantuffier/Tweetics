package fr.jeantuffier.tweetics.domain.usecase.link

import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.domain.model.Link
import io.reactivex.Maybe
import io.reactivex.Single

class GetLocalLinkUseCase(private val linkDao: LinkDao) {

    fun getLinks(tweetId: String): Single<List<Link>> {
        return linkDao.getLinks(tweetId)
            .switchIfEmpty(Maybe.just(emptyList()))
            .toSingle()
            .map { createLinks(tweetId, it) }
    }

    private fun createLinks(tweetId: String, linkEntities: List<LinkEntity>): List<Link> {
        return linkEntities.map {
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
        val array = stringIndices.split(",")
        return IntRange(array[0].toInt(), array[1].toInt())
    }

}
