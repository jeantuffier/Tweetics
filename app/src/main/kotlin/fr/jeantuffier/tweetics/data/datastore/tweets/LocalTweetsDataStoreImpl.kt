package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.mapper.TweetsMapper
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalTweetsDataStoreImpl @Inject constructor(
    private val tweetDao: TweetDao,
    private val tweetMapper: TweetsMapper
) : LocalTweetsDataStore {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return tweetDao
            .getTweets(screenName)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { tweetMapper.entitiesToModels(it) }
            .toSingle()
    }

    override fun saveTweets(screenName: String, tweets: List<Tweet>, doOnNext: (String) -> Unit) {
        if (tweets.isNotEmpty()) {
            Observable
                .fromCallable { tweetMapper.modelsToEntities(tweets, screenName) }
                .map { tweetDao.insertAll(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { doOnNext(screenName) }
                .subscribe()
        }
    }

}
