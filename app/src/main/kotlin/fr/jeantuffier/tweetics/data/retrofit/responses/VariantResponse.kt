package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class VariantResponse(
    @SerializedName("bitrate")
    val bitrate: Int,

    @SerializedName("content_type")
    val contentType: String,

    @SerializedName("url")
    val url: String
)
