package dev.thedukerchip.scandy.data.history

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScannedHistoryRepo @Inject constructor(
    private val scannedHistoryDao: ScannedHistoryDao
) {

    suspend fun addToHistory(item: ScannedHistoryItem) {
        scannedHistoryDao.addToHistory(item)
    }
}