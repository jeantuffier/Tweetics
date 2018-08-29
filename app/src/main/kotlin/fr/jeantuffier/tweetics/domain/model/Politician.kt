package fr.jeantuffier.tweetics.domain.model

data class Politician(
    val id: String,
    val groups: List<String>,
    val name: String,
    val pictureUrl: String,
    val role: String,
    val screenName: String
)
