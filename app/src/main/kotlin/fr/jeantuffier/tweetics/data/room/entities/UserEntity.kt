package fr.jeantuffier.tweetics.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "screen_name")
    val screenName: String,

    @ColumnInfo(name = "picture_url")
    val pictureUrl: String
)
