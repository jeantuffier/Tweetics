package fr.jeantuffier.tweetics.data.retrofit.service

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import io.reactivex.Single
import retrofit2.http.GET

interface PoliticianService {

    @GET("/politicians")
    fun getPoliticians(): Single<List<PoliticianResponse>>
}