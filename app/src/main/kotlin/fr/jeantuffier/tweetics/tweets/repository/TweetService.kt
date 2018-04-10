package fr.jeantuffier.tweetics.tweets.repository

import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

private const val SCREEN_NAME = "screenName"

interface TweetService {

    @GET("/politicians/{$SCREEN_NAME}")
    fun getTweets(@Path(SCREEN_NAME) screenName: String): Observable<List<Tweet>>
}
