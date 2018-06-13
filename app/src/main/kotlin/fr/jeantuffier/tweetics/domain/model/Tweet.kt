package fr.jeantuffier.tweetics.domain.model

import fr.jeantuffier.tweetics.presentation.common.Config

data class Tweet(
    val id: String,
    val screenName: String,
    val createdAt: String,
    val fullText: String,
    val reTweet: Tweet?,
    val links: List<Link>?,
    val medias: List<Media>,
    val displayTextRange: IntRange
) {

    val content by lazy {
        fullText.substring(
            displayTextRange.start,
            displayTextRange.endInclusive
        )
    }

    val hashTags by lazy { filterLinksByType(Link.Companion.LinkType.HASH_TAG) }

    val urls by lazy { filterLinksByType(Link.Companion.LinkType.URL) }

    val userMentions by lazy { filterLinksByType(Link.Companion.LinkType.USER_MENTION) }

    val reTweetUserMention by lazy {
        val screenName = filterLinksByType(Link.Companion.LinkType.USER_MENTION)
            ?.first()
            ?.text ?: ""
        "RT @$screenName: "
    }

    private fun filterLinksByType(type: Link.Companion.LinkType): List<Link>? {
        return links?.filter { it.type == type }
    }


    fun getTweetUrl() = "${Config.TWITTER}/$id"
}
