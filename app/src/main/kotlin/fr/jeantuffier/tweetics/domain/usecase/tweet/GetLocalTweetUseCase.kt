package fr.jeantuffier.tweetics.domain.usecase.tweet

import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.usecase.link.GetLocalLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.GetLocalPoliticianUseCase
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class GetLocalTweetUseCase(
    private val getLocalLinkUseCase: GetLocalLinkUseCase,
    private val getLocalPoliticianUseCase: GetLocalPoliticianUseCase,
    private val tweetDao: TweetDao
) {

    fun getWallTweets(): Single<List<Tweet>> {
        return tweetDao.getTweetsByDate()
            .switchIfEmpty(Maybe.just(emptyList()))
            .toObservable()
            .flatMap { getPoliticianTweetMap(it) }
            .flatMap { createTweet(it) }
            .toList()
    }

    private fun getPoliticianTweetMap(entities: List<TweetEntity>): Observable<Pair<Politician, TweetEntity>> {
        return Observable.just(entities)
            .flatMapIterable { it }
            .flatMap { tweetEntity ->
                getLocalPoliticianUseCase.get(tweetEntity.politicianId)
                    .toObservable()
                    .map { politician -> Pair(politician, tweetEntity) }
            }
    }

    private fun createTweet(pair: Pair<Politician, TweetEntity>): Observable<Tweet> {
        return Observable.just(pair)
            .flatMap { (politician, tweetEntity) ->
                getLocalLinkUseCase.getLinks(tweetEntity.id)
                    .toObservable()
                    .map { links ->
                        TweetFactory.mapToTweet(tweetEntity, politician, links)
                    }
            }
    }

}
