package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class VideoInfoResponse(
    @SerializedName("variants")
    val variants: List<VariantResponse>
)