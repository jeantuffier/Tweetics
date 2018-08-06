package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.model.User
import io.reactivex.Maybe

interface LocalWallDataStore {

    fun getLinks(): Maybe<List<Link>>

    fun getTweets(
        links: List<Link>,
        user: User
    ): Maybe<List<Tweet>>

    fun getUser(screenName: String): Maybe<User>

    fun saveTweets(
        tweets: List<Tweet>,
        doOnNext: (String) -> Unit
    )

}
