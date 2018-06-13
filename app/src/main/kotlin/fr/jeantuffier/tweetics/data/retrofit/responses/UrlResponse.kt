package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class UrlResponse(

    @SerializedName("url")
    val url: String,

    @SerializedName("indices")
    val indices: List<Int>

)
