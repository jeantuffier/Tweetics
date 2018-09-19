package fr.jeantuffier.tweetics.domain.usecase.tweet

import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.usecase.link.InsertLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.InsertPoliticianUseCase
import io.reactivex.Completable

class InsertTweetUseCase(
    private val insertLinkUseCase: InsertLinkUseCase,
    private val insertPoliticianUseCase: InsertPoliticianUseCase,
    private val tweetDao: TweetDao
) {

    fun insert(tweets: List<Tweet>): Completable {
        return Completable.fromCallable {
            tweets.forEach { insert(it) }
        }
    }

    private fun insert(tweet: Tweet) {
        insertPoliticianUseCase.insert(tweet.politician)

        val entity = TweetFactory.mapToEntity(tweet)
        tweetDao.insert(entity)

        insertLinkUseCase.insert(tweet.links)
    }

}
