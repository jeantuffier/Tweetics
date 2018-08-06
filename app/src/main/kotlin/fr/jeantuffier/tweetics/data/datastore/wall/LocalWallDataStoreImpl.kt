package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.LinkDao
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.dao.UserDao
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.data.room.entities.UserEntity
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalWallDataStoreImpl(
    private val linkDao: LinkDao,
    private val tweetDao: TweetDao,
    private val userDao: UserDao,
    private val factory: TweetsFactory
) : LocalWallDataStore {

    override fun getLinks(): Maybe<List<Link>> {
        return linkDao.getLinks(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { createLinks(it) }
    }

    private fun createLinks(linkEntities: List<LinkEntity>): List<Link> {
        return linkEntities.map {
            Link(
                it.id,
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

    override fun getUser(screenName: String): Maybe<User> {
        return userDao.getUser(screenName)
            .map { User(it.id, it.name, it.pictureUrl) }
    }

    override fun getTweets(
        links: List<Link>,
        user: User
    ): Maybe<List<Tweet>> {
        return tweetDao
            .getTweets(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweetsFromLocal(it, Config.WALL_SCREEN_NAME, links, user) }
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
