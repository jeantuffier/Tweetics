package fr.jeantuffier.tweetics.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface LinkDao {

    @Query("SELECT * FROM Link WHERE politician_screen_name LIKE :screenName")
    fun getLinks(screenName: String): Maybe<List<LinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(link: LinkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(links: List<LinkEntity>)
}