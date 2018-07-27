package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalWallDataStoreImpl(
    private val tweetDao: TweetDao,
    private val factory: TweetsFactory
) : LocalWallDataStore {

    override fun getLinksAndMedia(): Single<Pair<List<Link>, List<Media>>> {

    }

    override fun getTweets(
        links: List<Link>,
        medias: List<Media>
    ): Single<List<Tweet>> {
        return tweetDao
            .getTweets(Config.WALL_SCREEN_NAME)
            .switchIfEmpty(Maybe.just(emptyList()))
            .map { factory.getTweets(it, Config.WALL_SCREEN_NAME, links, medias) }
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
