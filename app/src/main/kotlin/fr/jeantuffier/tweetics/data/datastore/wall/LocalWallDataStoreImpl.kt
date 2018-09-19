package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.domain.usecase.link.GetLocalLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.GetLocalPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.GetLocalTweetUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.InsertTweetUseCase

class LocalWallDataStoreImpl(
    private val getLinkUseCase: GetLocalLinkUseCase,
    private val getLocalPoliticianUseCase: GetLocalPoliticianUseCase,
    private val getLocalTweetUseCase: GetLocalTweetUseCase,
    private val insertTweetUseCase: InsertTweetUseCase
) : LocalWallDataStore {

    override fun getLinks(tweetId: String) = getLinkUseCase.getLinks(tweetId)

    override fun getPolitician(politicianId: String) = getLocalPoliticianUseCase.get(politicianId)

    override fun getTweets() = getLocalTweetUseCase.getWallTweets()

    override fun saveTweets(tweets: List<Tweet>) = insertTweetUseCase.insert(tweets)

}
