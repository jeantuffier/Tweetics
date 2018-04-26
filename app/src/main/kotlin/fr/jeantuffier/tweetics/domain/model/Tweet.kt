package fr.jeantuffier.tweetics.domain.model

data class Tweet(
    val id: String,
    val createdAt: String,
    val fullText: String,
    var screenName: String,
    val links: List<Link>,
    val reTweet: Tweet?
)
