package fr.jeantuffier.tweetics.domain.model

data class Media(
    val id: Long,
    val url: String,
    val type: String,
    val sizes: List<Size>
) {

    val smallSize by lazy { filterSizesBByType(Size.Companion.Type.SMALL) }

    val mediumSize by lazy { filterSizesBByType(Size.Companion.Type.MEDIUM) }

    val largeSize by lazy { filterSizesBByType(Size.Companion.Type.LARGE) }

    private fun filterSizesBByType(type: Size.Companion.Type) =
            sizes.filter { it.type == type }

}