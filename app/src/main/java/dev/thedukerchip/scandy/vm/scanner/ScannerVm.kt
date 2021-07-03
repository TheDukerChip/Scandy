package dev.thedukerchip.scandy.vm.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScannerVm : ViewModel() {

    private val _flashState = MutableLiveData<FlashState>(FlashState.OFF)
    val flashState: LiveData<FlashState> = _flashState

    fun processEvents(event: ScannerUiEvent) {
        when (event) {
            ScannerUiEvent.ToggleFlash -> toggleFlashState()
        }
    }

    private fun toggleFlashState() {
        val currState = _flashState.value ?: FlashState.OFF
        _flashState.value = currState.other()
    }
}