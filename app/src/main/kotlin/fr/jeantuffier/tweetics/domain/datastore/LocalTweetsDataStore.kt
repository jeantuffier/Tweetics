package fr.jeantuffier.tweetics.domain.datastore

import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface LocalTweetsDataStore {
    fun getTweets(
        screenName: String,
        hashTags: List<Link.HashTag>,
        userMentions: List<Link.UserMention>,
        urls: List<Link.Url>,
        media: List<Link.Url>
    ): Single<List<Tweet>>

    fun saveTweets(
        screenName: String,
        tweets: List<Tweet>,
        politicianId: String,
        doOnNext: (String) -> Unit
    )
}