package dev.thedukerchip.scandy.vm.scanner

sealed class FlashState {
    abstract fun other(): FlashState

    object ON : FlashState() {
        override fun other(): FlashState = OFF
    }

    object OFF : FlashState() {
        override fun other(): FlashState = ON
    }
}
