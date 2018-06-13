package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class UserMentionResponse(

    @SerializedName("screen_name")
    val screenName: String,

    @SerializedName("indices")
    val indices: List<Int>

)
