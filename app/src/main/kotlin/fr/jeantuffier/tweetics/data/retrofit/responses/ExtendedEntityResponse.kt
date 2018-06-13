package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class ExtendedEntityResponse (
    @SerializedName("media")
    val media: List<MediaResponse>
)