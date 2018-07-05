package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.domain.model.Tweet
import io.reactivex.Single

interface RemoteWallDataStore {
    fun getTweets(): Single<List<Tweet>>
}