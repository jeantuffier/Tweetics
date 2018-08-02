package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTweetDataStoreImpl(
    private val tweetDao: TweetDao,
    private val factory: TweetsFactory
) : LocalTweetsDataStore {

    override fun getTweets(
        links: List<Link>,
        medias: List<Media>,
        user: User
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweetsFromLocal(it, Config.WALL_SCREEN_NAME, links, user) }
            .toSingle()
    }

    override fun saveTweets(
        screenName: String,
        tweets: List<Tweet>,
        politicianId: String,
        doOnNext: (String) -> Unit
    ) {
        if (tweets.isNotEmpty()) {
            Observable
                .fromCallable { factory.getTweetEntities(tweets, screenName, politicianId) }
                .map { tweetDao.insertAll(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { doOnNext(screenName) }
                .subscribe()
        }
    }

}
