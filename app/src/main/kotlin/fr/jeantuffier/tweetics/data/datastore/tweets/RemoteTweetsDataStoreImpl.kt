package fr.jeantuffier.tweetics.data.datastore.tweets

import fr.jeantuffier.tweetics.data.mapper.TweetsMapper
import fr.jeantuffier.tweetics.data.retrofit.service.TweetsService
import fr.jeantuffier.tweetics.domain.datastore.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class RemoteTweetsDataStoreImpl @Inject constructor(
    private val tweetService: TweetsService,
    private val mapper: TweetsMapper
) : RemoteTweetsDataStore {

    override fun getTweets(screenName: String): Single<List<Tweet>> {
        return tweetService
            .getTweets(screenName)
            .map { mapper.responsesToModels(it, screenName) }
    }

}
