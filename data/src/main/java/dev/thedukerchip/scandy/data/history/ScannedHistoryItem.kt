package dev.thedukerchip.scandy.data.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.thedukerchip.scandy.data.scanner.BarcodeType
import java.io.Serializable

@Entity(tableName = "scanned_history")
data class ScannedHistoryItem(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "type")
    val type: BarcodeType,
) : Serializable