package fr.jeantuffier.tweetics.presentation.tweets

class TweetContract {

    interface View {
        fun updateViewState(state: TweetViewState)
    }

    interface Presenter {
        fun loadContent(screenName: String)
        fun getImageUrl(screenName: String): String
        fun setView(view: View)
    }

}