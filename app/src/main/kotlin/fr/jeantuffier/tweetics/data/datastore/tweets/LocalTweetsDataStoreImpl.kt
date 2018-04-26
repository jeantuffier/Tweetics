package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.mapper.BiDirectionalMapper
import fr.jeantuffier.tweetics.domain.mapper.Mapper
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalTweetsDataStoreImpl @Inject constructor(
    private val tweetDao: TweetDao,
    private val tweetMapper: BiDirectionalMapper<TweetEntity, Tweet>
) : LocalTweetsDataStore {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { tweetMapper.toRight(it) }
            .toSingle()
    }

    override fun saveTweets(screenName: String, tweets: List<Tweet>, doOnNext: (String) -> Unit) {
        if (tweets.isNotEmpty()) {
            for (tweet in tweets) {
                tweet.screenName = screenName
            }

            Observable
                .fromCallable { tweetMapper.toLeft(tweets) }
                .map { tweetDao.insertAll(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { doOnNext(screenName) }
                .subscribe()
        }
    }

}
