package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface RemoteTweetsDataStore {
    fun getTweets(screenName: String): Single<List<Tweet>>
}