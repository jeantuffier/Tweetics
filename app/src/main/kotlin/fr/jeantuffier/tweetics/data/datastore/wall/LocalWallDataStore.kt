package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalWallDataStore {

    fun getTweets(
        links: List<Link>,
        medias: List<Media>
    ): Single<List<Tweet>>

    fun saveTweets(
        tweets: List<Tweet>,
        politicianId: String,
        doOnNext: (String) -> Unit
    )

}
