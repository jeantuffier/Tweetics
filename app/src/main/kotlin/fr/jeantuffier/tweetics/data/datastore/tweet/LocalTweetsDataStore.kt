package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalTweetsDataStore {
    fun getTweets(
        screenName: String,
        links: List<Link>,
        medias: List<Media>
    ): Single<List<Tweet>>

    fun saveTweets(
        screenName: String,
        tweets: List<Tweet>,
        politicianId: String,
        doOnNext: (String) -> Unit
    )
}
