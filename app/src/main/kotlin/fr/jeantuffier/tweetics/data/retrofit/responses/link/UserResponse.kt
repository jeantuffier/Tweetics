package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("id_str")
    val idStr: String

)