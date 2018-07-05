package fr.jeantuffier.tweetics.presentation.wall

class WallContract {

    interface View

    interface Presenter {
        fun attach(view: View)
        fun getImageUrl(screenName: String): String
        fun loadContent(screenName: String)
    }

}
