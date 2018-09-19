package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Completable
import io.reactivex.Single

interface LocalTweetsDataStore {
    fun getTweets(
        medias: List<Media>,
        politician: Politician
    ): Single<List<Tweet>>

    fun saveTweets(tweets: List<Tweet>): Completable
}
