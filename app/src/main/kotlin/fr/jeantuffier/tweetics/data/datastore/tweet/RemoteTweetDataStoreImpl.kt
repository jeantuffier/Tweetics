package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.retrofit.service.TweetsService
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

class RemoteTweetDataStoreImpl(
    private val tweetService: TweetsService,
    private val factory: TweetsFactory
) : RemoteTweetsDataStore {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return tweetService
            .getTweets(screenName)
            .map { factory.getTweets(it, screenName) }
    }

}
