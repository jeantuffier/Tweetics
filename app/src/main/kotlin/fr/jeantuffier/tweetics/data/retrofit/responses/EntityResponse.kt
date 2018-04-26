package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class EntityResponse (

    @SerializedName("hashtag")
    val hashTah: HashTagResponse,

    @SerializedName("user_mention")
    val userMention: UserMentionResponse,

    @SerializedName("url")
    val url: UrlResponse,

    @SerializedName("media")
    val media: UrlResponse

)
