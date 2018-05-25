package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class SizeResponse(

    @SerializedName("w")
    val width: Int,

    @SerializedName("h")
    val height: Int,

    @SerializedName("resize")
    val resize: String

)
