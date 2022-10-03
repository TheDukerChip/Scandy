package dev.thedukerchip.scandy.data

import androidx.room.Database

@Database(entities = [ScannedHistoryItem::class], version = 1)
abstract class ScandyDatabase {

    abstract fun scannedHistoryDao(): ScannedHistoryDao
}