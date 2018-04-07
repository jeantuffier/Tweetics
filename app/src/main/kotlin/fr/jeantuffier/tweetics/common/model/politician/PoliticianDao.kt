package fr.jeantuffier.tweetics.common.model.politician

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PoliticianDao {

    @Query("SELECT * FROM Politician")
    fun getPoliticians(): Single<List<Politician>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(politician: Politician)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(politicians: List<Politician>)
}
