package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.domain.model.Politician

sealed class PoliticianViewState {

    class Loading : PoliticianViewState()

    data class Loaded(val politicians: List<Politician>) : PoliticianViewState()

    class Error : PoliticianViewState()

}
