package fr.jeantuffier.tweetics.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Politician")
data class PoliticianEntity(
    @PrimaryKey()
    @ColumnInfo(name = "_id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "profile_image_url")
    val image: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "screen_name")
    val screenName: String

)
