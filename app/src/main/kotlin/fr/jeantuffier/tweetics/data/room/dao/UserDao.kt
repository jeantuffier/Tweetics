package fr.jeantuffier.tweetics.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import fr.jeantuffier.tweetics.data.room.entities.UserEntity
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE screen_name LIKE :screenName")
    fun getUser(screenName: String): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<UserEntity>)

}
