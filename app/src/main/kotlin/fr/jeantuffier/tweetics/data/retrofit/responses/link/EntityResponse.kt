package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class EntityResponse (

    @SerializedName("hashtag")
    val hashTah: List<HashTagResponse>,

    @SerializedName("user_mention")
    val userMention: List<UserMentionResponse>,

    @SerializedName("url")
    val url: List<UrlResponse>,

    @SerializedName("media")
    val media: List<UrlResponse>

)
