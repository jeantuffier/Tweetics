package fr.jeantuffier.tweetics.domain.model

sealed class Link {

    data class HashTag(
        val id: String,
        val text: String,
        val indices: IntRange
    ) : Link()

    data class UserMention(
        val id: String,
        val screenName: String,
        val indices: IntRange
    ) : Link()

    data class Url(
        val id: String,
        val url: String,
        val indices: IntRange
    ) : Link()

}