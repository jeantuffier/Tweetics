package fr.jeantuffier.tweetics.domain.usecase.link

import fr.jeantuffier.tweetics.data.factory.LinkFactory
import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.domain.model.Link
import io.reactivex.Maybe
import io.reactivex.Single

class GetLocalLinkUseCase(private val linkDao: LinkDao) {

    fun getLinks(tweetId: String): Single<List<Link>> {
        return linkDao.getLinks(tweetId)
            .switchIfEmpty(Maybe.just(emptyList()))
            .toSingle()
            .map { LinkFactory.mapToLinks(tweetId, it) }
    }

}
