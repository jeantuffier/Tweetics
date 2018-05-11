package fr.jeantuffier.tweetics.domain.model

sealed class Link {

    data class HashTag(
        val id: String,
        val text: String,
        val indices: List<Int>
    ) : Link()

    data class UserMention(
        val id: String,
        val screenName: String,
        val indices: List<Int>
    ) : Link()

    data class Url(
        val id: String,
        val url: String,
        val indices: List<Int>
    ) : Link()

}