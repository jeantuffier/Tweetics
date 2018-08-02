package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User
import io.reactivex.Single

interface LocalWallDataStore {

    fun getLinks(): Single<List<Link>>

    fun getTweets(
        links: List<Link>,
        user: User
    ): Single<List<Tweet>>

    fun getUser(screenName: String): Single<User>

    fun saveTweets(
        tweets: List<Tweet>,
        doOnNext: (String) -> Unit
    )

}
