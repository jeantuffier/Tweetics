package fr.jeantuffier.tweetics.data.repository

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Maybe

interface WallRepository {
    fun getTweets(): Maybe<List<Tweet>>
}