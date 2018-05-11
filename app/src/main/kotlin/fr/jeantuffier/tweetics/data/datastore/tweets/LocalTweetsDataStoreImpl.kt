package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.mapper.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalTweetsDataStoreImpl @Inject constructor(
    private val tweetDao: TweetDao,
    private val factory: TweetsFactory
) : LocalTweetsDataStore {

    override fun getTweets(
        screenName: String,
        hashTags: List<Link.HashTag>,
        userMentions: List<Link.UserMention>,
        urls: List<Link.Url>,
        media: List<Link.Url>
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweets(it, screenName, hashTags, userMentions, urls, media) }
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
