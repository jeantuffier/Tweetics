package fr.jeantuffier.tweetics.data.retrofit.responses.link

import com.google.gson.annotations.SerializedName

data class EntityResponse (

    @SerializedName("hashtags")
    val hashTags: List<HashTagResponse>,

    @SerializedName("user_mentions")
    val userMentions: List<UserMentionResponse>,

    @SerializedName("urls")
    val urls: List<UrlResponse>,

    @SerializedName("media")
    val media: List<MediaResponse>

)
