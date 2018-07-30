package fr.jeantuffier.tweetics.domain.model

data class Media(
    val id: Long,
    val url: String,
    val type: String,
    val sizes: List<Size>,
    val videoInfo: VideoInfo?
) {

    val smallSize by lazy { filterSizesByType(Size.Companion.Type.SMALL) }

    val mediumSize by lazy { filterSizesByType(Size.Companion.Type.MEDIUM) }

    val largeSize by lazy { filterSizesByType(Size.Companion.Type.LARGE) }

    private fun filterSizesByType(type: Size.Companion.Type) =
            sizes.filter { it.type == type }

}