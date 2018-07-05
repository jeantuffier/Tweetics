package fr.jeantuffier.tweetics.data.datastore.wall

import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.retrofit.service.WallService
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import io.reactivex.Single

class RemoteWallDataStoreImpl(
    private val wallService: WallService,
    private val factory: TweetsFactory
) : RemoteWallDataStore {

    override fun getTweets(): Single<List<Tweet>> {
        return wallService
            .getTweets()
            .map { factory.getTweets(it, Config.WALL_SCREEN_NAME) }
    }

}
