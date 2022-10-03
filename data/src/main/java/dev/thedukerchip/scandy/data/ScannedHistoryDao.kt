package dev.thedukerchip.scandy.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ScannedHistoryDao {

    @Query("select * from scanned_history")
    suspend fun getAllScannedHistory(): List<ScannedHistoryItem>
}