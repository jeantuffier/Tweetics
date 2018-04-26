package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class PoliticianResponse(

    @SerializedName("_id")
    val id: String,

    @SerializedName("screen_name")
    val screenName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("group")
    val group: String

)