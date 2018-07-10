package fr.jeantuffier.tweetics.presentation.wall

import fr.jeantuffier.tweetics.domain.model.Tweet

sealed class WallViewState {

    class Loading : WallViewState()

    data class Loaded(val tweets: List<Tweet>) : WallViewState()

    data class Error(val error: String?) : WallViewState()

}
