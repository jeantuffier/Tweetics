package fr.jeantuffier.tweetics.domain.model

data class Tweet(
    val id: String,
    val screenName: String,
    val createdAt: String,
    val fullText: String,
    val reTweet: Tweet?,
    val hashTags: List<Link.HashTag>?,
    val userMentions: List<Link.UserMention>?,
    val urls: List<Link.Url>?,
    val media: List<Link.Url>?,
    val displayTextRange: IntRange
)
