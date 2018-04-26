package fr.jeantuffier.tweetics.data.retrofit.service

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

private const val SCREEN_NAME = "screenName"

interface TweetService {

    @GET("/politicians/{$SCREEN_NAME}")
    fun getTweets(@Path(SCREEN_NAME) screenName: String): Single<List<TweetResponse>>
}
