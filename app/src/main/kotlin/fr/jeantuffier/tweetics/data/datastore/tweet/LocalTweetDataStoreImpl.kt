package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.usecase.tweet.GetLocalTweetUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.InsertTweetUseCase
import io.reactivex.Single

class LocalTweetDataStoreImpl(
    private val getLocalTweetUseCase: GetLocalTweetUseCase,
    private val insertTweetUseCase: InsertTweetUseCase
) : LocalTweetsDataStore {

    override fun getTweets(
        medias: List<Media>,
        politician: Politician
    ): Single<List<Tweet>> = getLocalTweetUseCase.getWallTweets()

    override fun saveTweets(tweets: List<Tweet>) = insertTweetUseCase.insert(tweets)

}
