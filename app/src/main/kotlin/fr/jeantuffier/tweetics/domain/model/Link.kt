package fr.jeantuffier.tweetics.domain.model

sealed class Link {

    data class HashTag(
        val id: String,
        val tweetId: String,
        val text: String,
        val indices: List<Int>
    ) : Link()

    data class Mention(
        val id: String,
        val tweetId: String,
        val screenName: String,
        val indices: List<Int>
    ) : Link()

    data class Url(
        val id: String,
        val tweetId: String,
        val url: String,
        val indices: List<Int>
    ) : Link()

    data class Media(
        val id: String,
        val tweetId: String,
        val url: String,
        val indices: List<Int>
    ) : Link()

}