package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalTweetDataStoreImpl(
    private val tweetDao: TweetDao
) : LocalTweetsDataStore {

    override fun getTweets(
        medias: List<Media>,
        politician: Politician
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { TweetFactory.mapToTweets(it, politician) }
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
                .fromCallable { TweetFactory.mapToTweetEntities(tweets) }
                .map { tweetDao.insertAll(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { doOnNext(screenName) }
                .subscribe()
        }
    }

}
