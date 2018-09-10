package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.LinkFactory
import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.functions.BiFunction

class LocalWallDataStoreImpl(
    private val linkDao: LinkDao,
    private val tweetDao: TweetDao,
    private val politicianDao: PoliticianDao
) : LocalWallDataStore {

    override fun getLinks(tweetId: String): Maybe<List<Link>> {
        return linkDao.getLinks(tweetId)
            .switchIfEmpty(Maybe.just(emptyList()))
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

    override fun getPolitician(screenName: String): Maybe<Politician> {
        return politicianDao.getPolitician(screenName)
            .map { PoliticianFactory.mapEntityToPolitician(it) }
    }

    override fun getTweets(): Maybe<List<Tweet>> {
        return Maybe.zip(
            getPolitician(Config.WALL_SCREEN_NAME),
            tweetDao.getTweets(Config.WALL_SCREEN_NAME),
            BiFunction { politician: Politician, tweets: List<TweetEntity> ->
                TweetFactory.mapToTweets(
                    tweets,
                    politician
                )
            }
        ).switchIfEmpty(Maybe.just(emptyList()))
    }

    override fun saveTweets(
        tweets: List<Tweet>,
        lambda: (String) -> Unit
    ) {
        if (tweets.isNotEmpty()) {
            tweets.forEach { tweet ->
                politicianDao.insert(getPoliticianEntity(tweet.politician))
                tweetDao.insert(getTweetEntity(tweet))
                linkDao.insertAll(getLinkEntities(tweet.links))
            }
            lambda(Config.WALL_SCREEN_NAME)
        }
    }

    private fun getPoliticianEntity(politician: Politician) =
        PoliticianFactory.mapPoliticianToEntity(politician)

    private fun getTweetEntity(tweet: Tweet) = TweetFactory.mapToEntity(tweet)

    private fun getLinkEntities(links: List<Link>?): List<LinkEntity> {
        return when (links) {
            null -> emptyList()
            else -> LinkFactory.mapToEntities(links)
        }
    }

}
