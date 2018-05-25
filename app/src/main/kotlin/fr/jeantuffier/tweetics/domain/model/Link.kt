package fr.jeantuffier.tweetics.domain.model

data class Link (
    val id: String,
    val text: String,
    val indices: IntRange,
    val type: LinkType
) {

    companion object {
        enum class LinkType { HASH_TAG, URL, USER_MENTION }
    }

}
