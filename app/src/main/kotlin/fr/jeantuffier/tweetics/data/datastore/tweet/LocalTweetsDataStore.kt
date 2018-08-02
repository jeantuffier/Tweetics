package fr.jeantuffier.tweetics.data.datastore.tweet

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User
import io.reactivex.Single

interface LocalTweetsDataStore {
    fun getTweets(
        links: List<Link>,
        medias: List<Media>,
        user: User
    ): Single<List<Tweet>>

    fun saveTweets(
        screenName: String,
        tweets: List<Tweet>,
        politicianId: String,
        doOnNext: (String) -> Unit
    )
}
