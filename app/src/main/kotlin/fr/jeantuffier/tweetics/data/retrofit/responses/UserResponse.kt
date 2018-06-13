package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("id_str")
    val idStr: String

)