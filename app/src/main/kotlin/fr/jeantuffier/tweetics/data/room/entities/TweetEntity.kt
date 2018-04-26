package fr.jeantuffier.tweetics.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Tweet")
data class TweetEntity(

    @PrimaryKey()
    @ColumnInfo(name = "_id")
    val id: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "full_text")
    val fullText: String,

    @ColumnInfo(name = "screen_name")
    var screenName: String
)
