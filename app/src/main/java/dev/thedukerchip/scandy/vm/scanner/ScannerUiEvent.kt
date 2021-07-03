package dev.thedukerchip.scandy.vm.scanner

sealed class ScannerUiEvent {
    object ToggleFlash: ScannerUiEvent()
}