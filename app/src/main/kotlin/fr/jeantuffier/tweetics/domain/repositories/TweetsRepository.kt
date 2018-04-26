package fr.jeantuffier.tweetics.domain.repositories

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface TweetsRepository {
    fun getTweets(screenName: String): Single<List<Tweet>>
}