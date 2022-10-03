package dev.thedukerchip.scandy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanned_history")
data class ScannedHistoryItem(
    @PrimaryKey val id: String,
    val content: String,
    val type: String,
)