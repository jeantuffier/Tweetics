package fr.jeantuffier.tweetics.common.model.tableupdate

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TableUpdate")
data class TableUpdate(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: Int,

    @ColumnInfo(name = "table_name")
    @SerializedName("table_name")
    val tableName: String,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    val timeStamp: Long
) {

    companion object {
        fun getDefault() = TableUpdate(0, "", 0)
    }
}