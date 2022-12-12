package dev.thedukerchip.scandy.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thedukerchip.scandy.camera.ScandyBarcode
import dev.thedukerchip.scandy.data.BarcodeToHistoryMapper
import dev.thedukerchip.scandy.data.history.ScannedHistoryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerVm @Inject constructor(
    private val repo: ScannedHistoryRepo
): ViewModel() {

    fun saveScannedItem(barcode: ScandyBarcode) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = BarcodeToHistoryMapper.toHistoryItem(barcode) ?: return@launch
            repo.addToHistory(item)
        }
    }
}