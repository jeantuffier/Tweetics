package fr.jeantuffier.tweetics.domain.model

data class Tweet(
    val id: String,
    val politicianId: String,
    val createdAt: String,
    val fullText: String,
    val retweetId: String
)
