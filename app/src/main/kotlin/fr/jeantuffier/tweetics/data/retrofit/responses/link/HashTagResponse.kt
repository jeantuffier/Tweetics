package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class HashTagResponse(

    @SerializedName("text")
    val text: String,

    @SerializedName("indices")
    val indices: List<Int>

)
