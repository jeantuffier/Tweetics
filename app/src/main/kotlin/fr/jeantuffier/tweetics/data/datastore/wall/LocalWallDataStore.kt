package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalWallDataStore {

    fun getLinks(): Single<List<Link>>

    fun getTweets(
        links: List<Link>
    ): Single<List<Tweet>>

    fun saveTweets(
        tweets: List<Tweet>,
        doOnNext: (String) -> Unit
    )

}
