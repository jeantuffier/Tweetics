package fr.jeantuffier.tweetics.presentation.wall

import fr.jeantuffier.tweetics.data.repository.TweetsRepository
import fr.jeantuffier.tweetics.presentation.common.Config

class WallPresenter(
    private val tweetRepository: TweetsRepository
): WallContract.Presenter {

    private lateinit var view: WallContract.View

    override fun attach(view: WallContract.View) {
        this.view = view
    }

    override fun getImageUrl(screenName: String)=
        "${Config.TWEETICS_SERVER_IMAGE}/$screenName.jpg"

    override fun loadContent(screenName: String) {

    }

}
