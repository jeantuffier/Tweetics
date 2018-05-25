package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class SizesResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("thumb")
    val thumb: SizeResponse,

    @SerializedName("small")
    val small: SizeResponse,

    @SerializedName("medium")
    val medium: SizeResponse,

    @SerializedName("large")
    val large: SizeResponse

)