package fr.jeantuffier.tweetics.domain.usecase.tweet

import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.model.Tweet

class InsertTweetUseCase(private val tweetDao: TweetDao) {

    fun insert(tweets: List<Tweet>) {
        val entities = TweetFactory.mapToEntities(tweets)
        tweetDao.insertAll(entities)
    }

    fun insert(tweet: Tweet) {
        val entity = TweetFactory.mapToEntity(tweet)
        tweetDao.insert(entity)
    }

}
