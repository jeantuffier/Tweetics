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
            .map { PoliticianFactory.getPoliticianFromLocal(it) }
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
        /*if (tweets.isNotEmpty()) {
            Completable

                //.andThen { insertUser(it.user) }
                //.andThen { insertTweets(tweets) }
                //.andThen { tweets.map { insertLinks(it.links) } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete { lambda(Config.WALL_SCREEN_NAME) }
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe()
        }*/
    }

    private fun insertPolitician(politician: Politician) {
        val politicianEntity = PoliticianFactory.getPoliticianEntity(politician)
        politicianDao.insert(politicianEntity)
    }

    private fun insertLinks(links: List<Link>?) {
        if (links != null) {
            val linkEntities = LinkFactory.mapToEntities(links)
            linkDao.insertAll(linkEntities)
        }
    }

    private fun insertTweets(tweets: List<Tweet>) {
        val tweetEntities = TweetFactory.mapToTweetEntities(tweets)
        tweetDao.insertAll(tweetEntities)
    }

}
