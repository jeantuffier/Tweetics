package fr.jeantuffier.tweetics.common.model.tableupdate

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface TableUpdateDao {

    @Query("SELECT * FROM TableUpdate WHERE table_name LIKE :tableName")
    fun getTableUpdate(tableName: String): Maybe<TableUpdate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tableUpdate: TableUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tableUpdates: List<TableUpdate>)
}
