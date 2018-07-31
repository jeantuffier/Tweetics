package fr.jeantuffier.tweetics.data.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "Link",
    foreignKeys = [ForeignKey(
        entity = TweetEntity::class,
        parentColumns = ["_id"],
        childColumns = ["tweet_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int,

    @ColumnInfo(name = "tweet_id")
    val tweetId: Int,

    @ColumnInfo(name = "politician_screen_name")
    val politicianScreenName: String,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "indices")
    val indices: String,

    @ColumnInfo(name = "is_tweet_url")
    val isTweetUrl: Boolean,

    @ColumnInfo(name = "link_type")
    val linkType: String
)
