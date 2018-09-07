package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class PoliticianResponse(

    @SerializedName("id_str")
    val id: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("profile_image_url_https")
    val image: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("screen_name")
    val screenName: String

)
