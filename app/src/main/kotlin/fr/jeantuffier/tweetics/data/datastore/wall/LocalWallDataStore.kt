package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalWallDataStore {

    fun getLinks(tweetId: String): Single<List<Link>>

    fun getPolitician(politicianId: String): Single<Politician?>

    fun getTweets(): Single<List<Tweet>>

    fun saveTweets(tweets: List<Tweet>)

}
