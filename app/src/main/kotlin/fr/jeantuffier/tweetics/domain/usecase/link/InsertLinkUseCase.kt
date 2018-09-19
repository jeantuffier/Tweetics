package fr.jeantuffier.tweetics.domain.usecase.link

import fr.jeantuffier.tweetics.data.factory.LinkFactory
import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.domain.model.Link

class InsertLinkUseCase(private val linkDao: LinkDao) {

    fun insert(links: List<Link>) {
        val entities = LinkFactory.mapToEntities(links)
        linkDao.insertAll(entities)
    }

    fun insert(link: Link) {
        val entity = LinkFactory.mapToEntity(link)
        linkDao.insert(entity)
    }

}
