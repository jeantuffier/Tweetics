package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import fr.jeantuffier.tweetics.data.retrofit.service.TweetService
import fr.jeantuffier.tweetics.domain.datastore.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.domain.mapper.Mapper
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class RemoteTweetsDataStoreImpl @Inject constructor(
    private val tweetService: TweetService,
    private val mapper: Mapper<TweetResponse, Tweet>
) : RemoteTweetsDataStore {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return tweetService
            .getTweets(screenName)
            .map { mapper.toRight(it) }
    }

}
