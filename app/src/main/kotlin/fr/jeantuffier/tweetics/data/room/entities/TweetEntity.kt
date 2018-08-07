package fr.jeantuffier.tweetics.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "Tweet",
    foreignKeys = [ForeignKey(
        entity = PoliticianEntity::class,
        parentColumns = ["_id"],
        childColumns = ["politician_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TweetEntity(

    @PrimaryKey()
    @ColumnInfo(name = "_id")
    val id: String,

    @ColumnInfo(name = "politician_id")
    val politicianId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "full_text")
    val fullText: String,

    @ColumnInfo(name = "rt_id")
    var retweetId: String,

    @ColumnInfo(name = "display_text_range")
    var displayTextRange: String

)
