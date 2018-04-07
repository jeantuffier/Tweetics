package fr.jeantuffier.tweetics.common.model.tweet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TweetWrapper")
data class TweetWrapper(

    @PrimaryKey()
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: String,

    @ColumnInfo(name = "screen_name")
    @SerializedName("screen_name")
    val screenName: String,

    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    val lastUpdate: String
) {

    @Ignore
    @SerializedName("tweets")
    var tweets: List<Tweet>? = null

    companion object {
        fun getDefault() = TweetWrapper("", "", "")
    }
}
