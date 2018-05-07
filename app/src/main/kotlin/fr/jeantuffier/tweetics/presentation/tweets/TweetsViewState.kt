package fr.jeantuffier.tweetics.presentation.tweets

import fr.jeantuffier.tweetics.domain.model.Tweet

sealed class TweetsViewState {

    class Loading : TweetsViewState()

    data class Loaded(val tweets: List<Tweet>) : TweetsViewState()

    data class Error(val error: String?) : TweetsViewState()

}