package dev.thedukerchip.scandy.data.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScannedHistoryDao {

    @Query("select * from scanned_history")
    suspend fun getAllScannedHistory(): List<ScannedHistoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToHistory(vararg item: ScannedHistoryItem)
}