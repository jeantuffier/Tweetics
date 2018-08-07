package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("id_str")
    val idStr: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_image_url_https")
    val picture: String,

    @SerializedName("screen_name")
    val screenName: String

)