package fr.jeantuffier.tweetics.data.retrofit.service

import fr.jeantuffier.tweetics.data.retrofit.responses.TweetResponse
import io.reactivex.Single
import retrofit2.http.GET

interface WallService {

    @GET("/wall")
    fun getTweets(): Single<List<TweetResponse>>
}