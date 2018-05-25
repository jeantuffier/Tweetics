package fr.jeantuffier.tweetics.domain.model

data class Size (
    val id: String,
    val width: Int,
    val height: Int,
    val resize: String,
    val type: Type
) {

    companion object {
        enum class Type { THUMB, SMALL, MEDIUM, LARGE }
    }

}
