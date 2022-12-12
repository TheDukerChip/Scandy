package dev.thedukerchip.scandy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.thedukerchip.scandy.data.history.ScannedHistoryDao
import dev.thedukerchip.scandy.data.history.ScannedHistoryItem
import dev.thedukerchip.scandy.data.scanner.BarcodeTypeConverter

@Database(entities = [ScannedHistoryItem::class], version = 1)
@TypeConverters(BarcodeTypeConverter::class)
abstract class ScandyDatabase: RoomDatabase() {

    abstract fun scannedHistoryDao(): ScannedHistoryDao
}