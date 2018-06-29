package fr.jeantuffier.tweetics.presentation.tweets

class TweetsContract {

    interface View {
        fun updateViewState(state: TweetsViewState)
    }

    interface Presenter {
        fun loadContent(screenName: String)
        fun getImageUrl(screenName: String): String
        fun setView(view: View)
    }

}