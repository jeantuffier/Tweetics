package fr.jeantuffier.tweetics.tweets.repository

import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

private const val SCREEN_NAME = "screenName"

interface TweetService {

    @GET("/politicians/{$SCREEN_NAME}")
    fun getTweetWrapper(@Path(SCREEN_NAME) screenName: String): Observable<TweetWrapper>
}
