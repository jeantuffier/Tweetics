package fr.jeantuffier.tweetics.presentation.wall

class WallContract {

    interface View {
        fun updateViewState(state: WallViewState)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadContent()
    }

}
