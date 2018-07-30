package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalWallDataStoreImpl(
    private val linkDao: LinkDao,
    private val tweetDao: TweetDao,
    private val factory: TweetsFactory
) : LocalWallDataStore {

    override fun getLinks(): Single<List<Link>> {
        return linkDao.getLinks(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { getLinks(it) }
            .toSingle()
    }

    private fun getLinks(linkEntities: List<LinkEntity>): List<Link> {
        return linkEntities.map {
            Link(
                it.id.toString(),
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

    override fun getTweets(
        links: List<Link>
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweets(it, Config.WALL_SCREEN_NAME, links) }
            .toSingle()
    }

    override fun saveTweets(
        tweets: List<Tweet>,
        doOnNext: (String) -> Unit
    ) {
        if (tweets.isNotEmpty()) {
            Observable
                .fromCallable {
                    factory.getTweetEntities(
                        tweets,
                        Config.WALL_SCREEN_NAME,
                        Config.WALL_SCREEN_NAME
                    )
                }
                .map { tweetDao.insertAll(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { doOnNext(Config.WALL_SCREEN_NAME) }
                .subscribe()
        }
    }

}
