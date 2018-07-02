package fr.jeantuffier.tweetics.presentation.tweets

import fr.jeantuffier.tweetics.domain.model.Tweet

sealed class TweetViewState {

    class Loading : TweetViewState()

    data class Loaded(val tweets: List<Tweet>) : TweetViewState()

    data class Error(val error: String?) : TweetViewState()

}