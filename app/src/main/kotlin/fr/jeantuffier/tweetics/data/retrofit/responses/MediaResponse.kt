package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MediaResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("media_url_https")
    val url: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("sizes")
    val sizes: SizesResponse,

    @SerializedName("video_info")
    val videoInfo: VideoInfoResponse?

)