package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.retrofit.service.WallService
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Single

class RemoteWallDataStoreImpl(
    private val wallService: WallService
) : RemoteWallDataStore {

    override fun getTweets(): Single<List<Tweet>> {
        return wallService
            .getTweets()
            .map { TweetFactory.mapToTweets(it) }
    }

}
