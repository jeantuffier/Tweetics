package fr.jeantuffier.tweetics.domain.datastore

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalTweetsDataStore {
    fun getTweets(screenName: String): Single<List<Tweet>>
    fun saveTweets(screenName: String, tweets: List<Tweet>, doOnNext: (String) -> Unit)
}