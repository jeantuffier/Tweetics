package fr.jeantuffier.tweetics.presentation.politician

class PoliticianContract {

    interface View {
        fun updateViewState(state: PoliticianViewState)
    }

    interface Presenter {
        fun loadContent()
        fun setView(view: View)
    }

}