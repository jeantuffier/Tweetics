package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class TweetResponse(

    @SerializedName("id_str")
    val idStr: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("full_text")
    val fullText: String,

    @SerializedName("user")
    val user: UserResponse,

    @SerializedName("retweeted_status")
    val retweetedStatus: TweetResponse,

    @SerializedName("entities")
    val entities: List<EntityResponse>

)