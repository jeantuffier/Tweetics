package fr.jeantuffier.tweetics.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import io.reactivex.Single

@Dao
interface PoliticianDao {

    @Query("SELECT * FROM Politician")
    fun getPoliticians(): Single<List<PoliticianEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(politician: PoliticianEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(politicians: List<PoliticianEntity>)

    @Query("DELETE FROM Politician")
    fun clear()
}
