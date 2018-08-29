package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe

interface LocalWallDataStore {

    fun getLinks(tweetId: String): Maybe<List<Link>>

    fun getTweets(): Maybe<List<Tweet>>

    fun getPolitician(screenName: String): Maybe<Politician>

    fun saveTweets(
        tweets: List<Tweet>,
        doOnNext: (String) -> Unit
    )

}
