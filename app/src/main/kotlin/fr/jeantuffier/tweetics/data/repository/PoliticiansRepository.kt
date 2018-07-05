package fr.jeantuffier.tweetics.data.repository

import fr.jeantuffier.tweetics.domain.model.Politician
import io.reactivex.Single

interface PoliticiansRepository {
    fun getPoliticians(): Single<List<Politician>>
}