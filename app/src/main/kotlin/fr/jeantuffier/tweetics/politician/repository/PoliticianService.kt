package fr.jeantuffier.tweetics.politician.repository

import fr.jeantuffier.tweetics.common.model.politician.Politician
import io.reactivex.Observable
import retrofit2.http.GET

interface PoliticianService {

    @GET("/politicians")
    fun getPoliticians(): Observable<List<Politician>>
}