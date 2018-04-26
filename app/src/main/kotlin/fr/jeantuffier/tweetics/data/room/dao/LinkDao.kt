package fr.jeantuffier.tweetics.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import fr.jeantuffier.tweetics.data.room.entities.LinkEntity
import io.reactivex.Single

@Dao
interface LinkDao {

    @Query("SELECT * FROM Link")
    fun getPoliticians(): Single<List<LinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(politician: LinkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(politicians: List<LinkEntity>)
}