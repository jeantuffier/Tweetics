package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTweetsDataStoreImpl(
    private val tweetDao: TweetDao,
    private val factory: TweetsFactory
) : LocalTweetsDataStore {

    override fun getTweets(
        screenName: String,
        links: List<Link>,
        medias: List<Media>
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweets(it, screenName, links, medias) }
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
