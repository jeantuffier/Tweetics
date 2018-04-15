package fr.jeantuffier.tweetics.common.model.tweet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fr.jeantuffier.tweetics.common.Config

@Entity(tableName = "Tweet")
data class Tweet(

    @PrimaryKey()
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: String,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: String,

    @ColumnInfo(name = "full_text")
    @SerializedName("full_text")
    val fullText: String,

    @ColumnInfo(name = "screen_name")
    @SerializedName("screen_name")
    var screenName: String
) {

    fun getImageUrl() = "${Config.TWEETICS_SERVER_IMAGE}$screenName.jpg"
}
