package fr.jeantuffier.tweetics.common.model.politician

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fr.jeantuffier.tweetics.common.Config.TWEETICS_SERVER_IMAGE

@Entity(tableName = "Politician")
data class Politician(
    @PrimaryKey()
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: String,

    @ColumnInfo(name = "screen_name")
    @SerializedName("screen_name")
    val screenName: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "role")
    @SerializedName("role")
    val role: String,

    @ColumnInfo(name = "group")
    @SerializedName("group")
    val group: String
) {

    fun getImageUrl() = "$TWEETICS_SERVER_IMAGE$screenName.jpg"
}
