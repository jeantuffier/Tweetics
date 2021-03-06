package fr.jeantuffier.tweetics.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class TweetResponse(

    @SerializedName("id_str")
    val id: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("full_text")
    val fullText: String?,

    @SerializedName("user")
    val politician: PoliticianResponse,

    @SerializedName("retweeted_status")
    val retweetedStatus: TweetResponse?,

    @SerializedName("entities")
    val entities: EntityResponse?,

    @SerializedName("extended_entities")
    val extendedEntities: ExtendedEntityResponse?,

    @SerializedName("display_text_range")
    val displayTextRange: List<Int>?

)
