package fr.jeantuffier.tweetics.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity

@Database(
    entities = [PoliticianEntity::class, TweetEntity::class, LinkEntity::class],
    version = 6,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun politicianDao(): PoliticianDao

    abstract fun tweetDao(): TweetDao

}
