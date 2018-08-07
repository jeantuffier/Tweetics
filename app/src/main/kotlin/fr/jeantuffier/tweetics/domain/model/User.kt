package fr.jeantuffier.tweetics.domain.model

data class User(
    val id: String,
    val name: String,
    val pictureUrl: String,
    val screenName: String
)