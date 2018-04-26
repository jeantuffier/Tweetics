package fr.jeantuffier.tweetics.domain.datastore

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface RemoteTweetsDataStore {
    fun getTweets(screenName: String): Single<List<Tweet>>
}