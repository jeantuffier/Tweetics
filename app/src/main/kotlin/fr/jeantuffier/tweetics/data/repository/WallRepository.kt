package fr.jeantuffier.tweetics.data.repository

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface WallRepository {
    fun getTweets(): Single<List<Tweet>>
}
